package com.example.timetrace.thread

object ThreadHook {

    var hasHook=false
    var hookFailed=false

    fun enableThreadHook() {
        if (hasHook){
            return
        }
        hasHook=true
        enableThreadHookNative()
    }

    external fun enableThreadHookNative()

    init {
        System.loadLibrary("threadhook")
    }
}