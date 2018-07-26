package com.example.fcl.kotlindemo

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import android.os.Looper
import android.util.Printer
import com.example.fcl.dadademo.util.ActivityManager
import com.facebook.stetho.Stetho
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
        Stetho.initializeWithDefaults(this)
        registerActivityLifecycleCallbacks(object:Application.ActivityLifecycleCallbacks{
            override fun onActivityPaused(activity: Activity?) {
            }

            override fun onActivityResumed(activity: Activity?) {
                ActivityManager.currentActivity=activity
            }

            override fun onActivityStarted(activity: Activity?) {
            }

            override fun onActivityDestroyed(activity: Activity?) {
            }

            override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {
            }

            override fun onActivityStopped(activity: Activity?) {
            }

            override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
            }
        })
    }

    companion object {

        lateinit var instance: Context
            private set
    }

}