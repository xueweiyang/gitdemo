package com.example.interview.four

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log

class StartService : Service() {
    override fun onBind(intent: Intent?): IBinder {
        return MyBinder()
    }

}
class MyBinder : Binder() {


    fun test(){
        Log.e("Mybinder", "-----test")
    }
}