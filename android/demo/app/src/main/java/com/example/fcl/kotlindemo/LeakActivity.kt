package com.example.fcl.kotlindemo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class LeakActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leak)
        LeakInstance.getInstance(this)
        var leak = Leak.getLeak(this)
    }
}
