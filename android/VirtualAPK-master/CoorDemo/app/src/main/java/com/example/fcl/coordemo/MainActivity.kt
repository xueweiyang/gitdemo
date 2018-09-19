package com.example.fcl.coordemo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.recycler
import kotlinx.android.synthetic.main.activity_main.toolbar

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var datas= arrayListOf<String>()
        for (pos in 0..20) {
            datas.add("test:"+pos)
        }
        val adapter=MyAdapter(datas)
        recycler.adapter=adapter
        recycler.layoutManager=LinearLayoutManager(this)

        toolbar.title = "所有绘本"
    }
}
