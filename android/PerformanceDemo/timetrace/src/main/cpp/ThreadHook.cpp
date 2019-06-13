//
// Created by f on 19-6-13.
//

#include <jni.h>
#include <string>
#include <atomic>
#include <android/log.h>
#include "linker.h"
#include "hooks.h"
#include <pthread.h>

using namespace std;
#define  LOG_TAG    "HOOOOOOOOK"
#define  ALOG(...)  __android_log_print(ANDROID_LOG_ERROR,LOG_TAG,__VA_ARGS__)
atomic<bool> thread_hooked;

jclass kJavaClass;
jmethodID  kMethodGetStack;
JavaVM* kJvm;

void printJavaStack() {
    JNIEnv* jniEnv=NULL;
    kJvm->GetEnv()
}

int pthread_create_hook(pthread_t* thread, const pthread_attr_t* attr,
        void* (*start_routine)(void *), void* arg) {
    printJavaStack();
    return CALL_PREV(pthread_create_hook, thread,attr,*start_routine,arg);
}

void hookLoadedLibs() {
    ALOG("hook_plt_method");
    hook_plt_method("libart.so", "pthread_create", (hook_func) &pthread_create_hook);
}

void enableThreadHook() {
    if(thread_hooked){
        return;
    }
    ALOG("enableThreadHook");
    thread_hooked= true;
    if (linker_initialize()) {
        throw runtime_error("could not initialize linker library");
    }
    hookLoadedLibs();
}

extern "C"
JNIEXPORT void JNICALL
Java_com_example_timetrace_thread_ThreadHook_enableThreadHookNative(JNIEnv *env, jobject type) {
    enableThreadHook();
}

bool InitJniEnv(JavaVM* vm){
    kJvm=vm;
    JNIEnv* env=NULL;
    if(kJvm->GetEnv((void**)&env, JNI_VERSION_1_6) != JNI_OK) {
        ALOG("InitJniEnv GetEnv !JNI_OK");
        return false;
    }
    kJavaClass = reinterpret_cast<jclass >(env->NewGlobalRef(env->FindClass("com/dodola/thread/ThreadHook")));
}

JNIEXPORT jint JNICALL JNI_LOAD(JavaVM* vm, void* reserved){
    ALOG("JNI_OnLoad");
    if (!InitJniEnv(vm)){
        return -1;
    }
    return JNI_VERSION_1_6;
}