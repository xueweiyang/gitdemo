package com.example.fcl.focuddemo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.btn1
import kotlinx.android.synthetic.main.activity_main.et1
import kotlinx.android.synthetic.main.activity_main.layout1

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn1.setOnClickListener {
//            it.requestFocus()
            layout1.clearFocus()
            et1.clearFocus()
        }
        et1.setOnFocusChangeListener { v, hasFocus ->
            Log.e("MainA", "focus:"+hasFocus)
            et1.isCursorVisible = hasFocus
        }
    }
}
