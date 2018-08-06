package com.example.fcl.kotlindemo.test

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.fcl.kotlindemo.R
import kotlinx.android.synthetic.main.activity_espress.edit
import kotlinx.android.synthetic.main.activity_espress.text

class EspressActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_espress)
    }

    fun sayHello(view:View) {
        when(view.id){
            R.id.hello -> text.text = "hello, "+edit.text.toString()
            R.id.recycler -> startActivity(Intent(this, RecyclerActivity::class.java))
            R.id.web -> startActivity(Intent(this, WebActivity::class.java))
        }

    }
}
