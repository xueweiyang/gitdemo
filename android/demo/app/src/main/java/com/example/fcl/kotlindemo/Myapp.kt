package com.example.fcl.kotlindemo

import android.app.Application
import android.content.Context

class Myapp : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {

        lateinit var instance: Context
            private set
    }
}
