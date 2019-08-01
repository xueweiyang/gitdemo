package com.example.flutterdemo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.FrameLayout
import io.flutter.app.FlutterActivity
import io.flutter.facade.Flutter
import io.flutter.plugin.common.EventChannel
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugins.GeneratedPluginRegistrant
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    val TAG="MainActivity"
    val CHANNEL = "com.example.flutterDemo/test"
    val STREAM = "com.example.flutterDemo/stream"
    var timerDispose : Disposable?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)

        val flutterView = Flutter.createView(
            this,
            lifecycle,
            "/audiobook"
        )
//        val layout = FrameLayout.LayoutParams(600,800)
//        layout.leftMargin=100
//        layout.topMargin=200
        setContentView(flutterView)
//        GeneratedPluginRegistrant.registerWith(this)

        MethodChannel(flutterView, CHANNEL).setMethodCallHandler { methodCall, result ->
            if (methodCall.method == "changeLife") {
                val message = "Life changed"
                result.success(message)
            }
        }

        EventChannel(flutterView, STREAM).setStreamHandler(object : EventChannel.StreamHandler {
            override fun onListen(p0: Any?, events: EventChannel.EventSink?) {
                Log.d(TAG, "onlisten")
                timerDispose = Observable
                    .interval(0,1,TimeUnit.SECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe ({
                        events?.success(it)
                    },{
                        Log.d(TAG, "error:${it.message}")
                    })
            }

            override fun onCancel(p0: Any?) {
                if (timerDispose != null) {
                    timerDispose?.dispose()
                    timerDispose = null
                }
            }
        })
    }

    fun test(){
        for (i in 0..100000) {
            val a = UUID.randomUUID().toString()
            Log.e(TAG, "${a.substring(a.length-2).toLong(16)%100}")
        }
    }
}
