package com.example.fcl.leakdemo

import android.app.Application
import com.squareup.leakcanary.LeakCanary

/**
 * Created by galio.fang on 19-1-14
 */
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        LeakCanary.install(this)
    }
}
