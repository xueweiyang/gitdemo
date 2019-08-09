package com.example.interview

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {


     lateinit var a:String
    var name:String ="dddddddddddddd"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        n()
        m()


    }

    fun m() {
        val d = name.let {
            it.substring(1)
            "3223"
        }
        print(d)
    }

    fun n() {
        val d =name.apply {
            substring(1)
        }
        print(d)
    }

    companion object{
        val s="aa"
    }
}
