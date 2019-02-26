package com.example.fcl.lottieflicker

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.recycler

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val data = arrayListOf(true, false, false, true, true, false, false, true,
            false, true, true, false, false, true,true, false, false, true,
            false, true, true, false, false, true)
        val adapter = MyAdapter(data)
        recycler.adapter = adapter
        recycler.layoutManager = LinearLayoutManager(this)

    }
}
