package com.example.performancedemo

import android.os.Bundle
import android.os.Trace
import android.support.v7.app.AppCompatActivity
import android.util.Log
import kotlinx.android.synthetic.main.activity_main2.*

class Main2Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        start.setOnClickListener {
            //            startActivity(Intent(this, MainActivity::class.java))
//            Atrace.hasHacks()
//            TraceTag.i("android_sleep")
//            Thread.sleep(100)
//            TraceTag.o()

            TraceTag.i("thread_test")
            Thread {
                test()
            }.start()
            Thread {
                test()
            }.start()
            TraceTag.o()
            getIsArtInUse()
        }
//        val data= arrayListOf(1,2,4,5)
//        data.forEach {
//            if (it>2) {
//                return@forEach
//            }
//            Log.e("Main2", "value:$it")
//        }



    }

    fun getIsArtInUse() : Boolean{
        val version = System.getProperty("java.vm.version")
        Log.e("ttt", "version:$version")
        return version!=null && version.startsWith("2")
    }

    fun test() {
        synchronized(this){
            var a = 1
            a++
        }
    }
}
