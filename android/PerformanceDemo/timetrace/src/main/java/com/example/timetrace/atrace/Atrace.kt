package com.example.timetrace.atrace

object Atrace {

    var hasHook = false
    var hookFailed = false

    fun hasHacks():Boolean {
        if (!hasHook && !hookFailed) {
            hasHook = installSystraceHook()
            hookFailed = !hasHook
        }
        return hasHook
    }

    fun enableSystrace() {
        if (!hasHacks()) {
            return
        }
        enableSystraceNative()
    }

    external fun installSystraceHook(): Boolean
    external fun enableSystraceNative()

    init {
        System.loadLibrary("tracehook")
    }
}