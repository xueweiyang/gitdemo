package com.example.fcl.kotlindemo

import android.app.Application
import android.content.Context
import android.os.Looper
import android.util.Printer
import com.github.moduth.blockcanary.BlockCanary
import com.squareup.leakcanary.LeakCanary

class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
        if (LeakCanary.isInAnalyzerProcess(this)){
            return
        }
        LeakCanary.install(this)
        BlockCanary.install(this, MyBlockCanaryContext())
        Looper.getMainLooper().setMessageLogging(MyPrinter())
    }

    companion object {

        lateinit var instance: Context
            private set
    }

}