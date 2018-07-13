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
        if (LeakCanary.isInAnalyzerProcess(this)){
            return
        }
        LeakCanary.install(this)
        BlockCanary.install(this, MyBlockCanaryContext())
        Looper.getMainLooper().setMessageLogging(MyPrinter())
    }

    fun getContext() : Context {
        return this
    }

    companion object {
        val context : Context = MyApp().getContext()
    }
}