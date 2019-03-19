package com.example.fcl.leakdemo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.testView
import org.json.JSONObject
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    val handler = object : Handler() {
        override fun dispatchMessage(msg: Message?) {
            super.dispatchMessage(msg)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var co="ddaaabc:ddd"
        try {
            val obj=JSONObject(co)
            co = obj.optString("scheme")
        }catch (e:Exception) {

        }

        Log.e("what", co)

//        Thread(Runnable {
//            try {
//                Thread.sleep(15000)
//            } catch (e : Exception) {
//
//            }
//        }).start()
//
//        testView.setOnClickListener {
//            finish()
//        }
        handler.sendEmptyMessageDelayed(0, 60*1000)
    }
}
