#include <jni.h>
#include <string>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include "shmdata.h"

#include <sys/types.h> /* for open */
#include <sys/stat.h> /* for open */
#include <fcntl.h>     /* for open */
#include <signal.h>
#include <sys/mman.h>
#include <android/log.h>

#define LOGI(...) ((void)__android_log_print(ANDROID_LOG_INFO, "native-activity", __VA_ARGS__))
#define LOGW(...) ((void)__android_log_print(ANDROID_LOG_WARN, "native-activity", __VA_ARGS__))
#define LOGE(...) ((void)__android_log_print(ANDROID_LOG_ERROR, "native-activity", __VA_ARGS__))

extern "C"

JNIEXPORT jint JNICALL Java_com_example_fcl_cmakedemo_MainActivity_openMem(JNIEnv* env,jobject clazz,jstring name,jint
length) {
    shared_use_st *st=NULL;
    const char* namestr = (name ? env->GetStringUTFChars(name,NULL):NULL);
    int result=-1;
    if(access(namestr,F_OK)==0) {
        result = open(namestr,O_RDWR);
    } else {
        result = open(namestr,O_RDWR|O_CREAT);
        if(result>=0){
            st=(shared_use_st *)malloc(sizeof(shared_use_st));
            if(st==NULL){
LOGE("open malloc failed");

            }
            if (write(result,st,sizeof(shared_use_st))<0){
            LOGE("open write failed");
            }
            free(st);
        }
    }
    return result;

}
