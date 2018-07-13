package com.example.fcl.kotlindemo

import android.os.SystemClock
import android.util.Log
import android.util.Printer

class MyPrinter : Printer {

    private val TAG = MyPrinter::class.java.simpleName
    private var startTime = 0L
    private var startThreadTime = 0L
    private var startedPrinting = false

    override fun println(p0: String?) {
        if (!startedPrinting) {
            startTime = System.currentTimeMillis()
            startThreadTime = SystemClock.currentThreadTimeMillis()
            startedPrinting = true
        } else {
            val endTime = System.currentTimeMillis()
            startedPrinting = false
            if (isBlock(endTime)) {
                Log.e(TAG, "endTime:" + endTime)
            }
        }
    }

    fun isBlock(endTime: Long): Boolean {
        return endTime - startTime > 1000
    }
}