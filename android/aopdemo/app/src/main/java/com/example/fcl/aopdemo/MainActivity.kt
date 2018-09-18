package com.example.fcl.aopdemo

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.test

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        startActivity(Intent(this,Main2Activity::class.java))

//        test.setOnClickListener {
//            Toast.makeText(this, "点击", Toast.LENGTH_SHORT).show()
//        }
    }
}
