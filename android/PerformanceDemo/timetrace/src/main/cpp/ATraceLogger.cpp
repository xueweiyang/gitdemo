//
// Created by f on 19-6-13.
//

#include <jni.h>
#include <string>
#include <android/log.h>
#include <dlfcn.h>

#include "linker.h"
#include "hooks.h"

#define LOG_TAG "HOOOOOOOOK"
#define ALOG(...) __android_log_print(ANDROID_LOG_INFO,LOG_TAG,__VA_ARGS__)
using namespace std;
#define PROP_VALUE_MAX 92

int *atrace_marker_fd = nullptr;
atomic<uint64_t> *atrace_anabled_tags = nullptr;
atomic<uint64_t> original_tags(UINT64_MAX);
atomic<bool> systrace_installed;
bool first_enable = true;

int getAndroidSdk() {
    auto android_sdk = ([] {
        char sdk_version_str[PROP_VALUE_MAX];
        __system_property_get("ro.build.version.sdk", sdk_version_str);
        return atoi(sdk_version_str);
    })();
    return android_sdk;
}

void log_systrace(const void *buf, size_t count) {
    const char *msg = reinterpret_cast<const char *>(buf);

    switch (msg[0]) {
        case 'B': {//begin synchronous event. format: "B|<pid>|<name>"
            ALOG("=========== %s", msg);
            break;
        }
        case 'E': { // end synchronous event. format: "E"
            ALOG("=========== E");
            break;
        }
        case 'S': // start async event. format: "S|<pid>|<name>|<cookie>"
        case 'F': // finish async event. format: "F|<pid>|<name>|<cookie>"
        case 'C': // counter. format: "C|<pid>|<name>|<value>"
        default:
            return;
    }
}

/**
 * ftrace 所有性能埋点数据都会通过 trace_mark文件写入内核缓冲区，所以hook该文件
 * @param fd
 * @param count
 * @return
 */
bool should_log_systrace(int fd, size_t count) {
    return systrace_installed && fd == *atrace_marker_fd && count > 0;
}

ssize_t write_hook(int fd, const void *buf, size_t count) {
    if (should_log_systrace(fd, count)) {
        log_systrace(buf, count);
        return count;
    }
    return CALL_PREV(write_hook, fd, buf, count);
}

ssize_t __write_chk_hook(int fd, const void *buf,size_t count, size_t buf_size) {
    if (should_log_systrace(fd,count)){
        log_systrace(buf,count);
        return count;
    }
    return CALL_PREV(__write_chk_hook,fd,buf,count,buf_size);
}

/**
 * plt hook libc 的write方法
 */
void hookLoadedLibs() {
    hook_plt_method("libc.so", "write", (hook_func) &write_hook);
    hook_plt_method("libc.so", "__write_chk", (hook_func) &__write_chk_hook);
}

void installSystraceSnooper() {
    auto sdk = getAndroidSdk();
    {
        string libname("libcutils.so");
        string enabled_tags_sym("atrace_enabled_tags");
        string fd_sym("atrace_marker_fd");

        if (sdk < 18) {
            libname = "libutils.so";
            enabled_tags_sym = "_ZN7android6Tracer12sEnabledTagsE";
            fd_sym = "_ZN7android6Tracer8sTraceFDE";
        }

        void *handle;
        if (sdk < 21) {
            handle = dlopen(libname.c_str(), RTLD_LOCAL);
        } else {
            handle = dlopen(nullptr, RTLD_GLOBAL);
        }

        atrace_anabled_tags = reinterpret_cast<atomic<uint64_t> *>(
                dlsym(handle, enabled_tags_sym.c_str())
        );

        if (atrace_anabled_tags == nullptr) {
            throw runtime_error("enabled tags not defined");
        }

        atrace_marker_fd = reinterpret_cast<int *>(
                dlsym(handle, fd_sym.c_str())
        );
        if (atrace_marker_fd == nullptr) {
            throw runtime_error("trace fd not defined");
        }
        if (*atrace_marker_fd == -1) {
            throw runtime_error("trace fd not valid");
        }
    }

    hookLoadedLibs();

    systrace_installed = true;
}

bool installSystraceHook() {
    try {
        installSystraceSnooper();
        return true;
    } catch (const runtime_error &e) {
        return false;
    }
}

void enableSystrace() {
    if(!systrace_installed){
        return;
    }
    if(!first_enable){
        try {
            hookLoadedLibs();
        } catch (...){

        }
    }
    first_enable = false;
    auto prev = atrace_anabled_tags->exchange(UINT64_MAX);
    if (prev!= UINT64_MAX) {
        original_tags = prev;
    }
}

extern "C"
JNIEXPORT jboolean JNICALL
Java_com_example_timetrace_atrace_Atrace_installSystraceHook(JNIEnv *env, jobject type) {
    installSystraceHook();
    return static_cast<jboolean>(true);
}

extern "C"
JNIEXPORT void JNICALL
Java_com_example_timetrace_atrace_Atrace_enableSystraceNative(JNIEnv *env, jobject type) {
}