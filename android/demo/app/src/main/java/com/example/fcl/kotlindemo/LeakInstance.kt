package com.example.fcl.kotlindemo

import android.content.Context

class LeakInstance(context: Context) {

    companion object {
        var instance : LeakInstance? = null

        fun getInstance(context: Context) : LeakInstance {
            if (instance == null) {
                instance = LeakInstance(context)
            }
            return instance!!
        }
    }

}