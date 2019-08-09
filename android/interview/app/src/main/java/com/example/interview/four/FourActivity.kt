package com.example.interview.four

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import com.example.interview.R

class FourActivity : AppCompatActivity() {

    var binder:MyBinder?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_four)
        val intent=Intent(this, StartService::class.java)
        bindService(intent,Myconn(), Context.BIND_AUTO_CREATE)
    }

    inner class Myconn : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName?) {

        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            binder = service as MyBinder
            binder?.test()
        }

    }
}
