package com.example.performancedemo

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.timetrace.atrace.Atrace
import kotlinx.android.synthetic.main.activity_main2.*

class Main2Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        start.setOnClickListener {
            //            startActivity(Intent(this, MainActivity::class.java))
            Atrace.hasHacks()
        }
//        val data= arrayListOf(1,2,4,5)
//        data.forEach {
//            if (it>2) {
//                return@forEach
//            }
//            Log.e("Main2", "value:$it")
//        }

    }
}
