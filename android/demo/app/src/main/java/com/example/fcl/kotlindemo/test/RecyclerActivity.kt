package com.example.fcl.kotlindemo.test

import android.content.Context
import android.content.DialogInterface
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView.Adapter
import android.support.v7.widget.RecyclerView.ViewHolder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.fcl.kotlindemo.R
import kotlinx.android.synthetic.main.activity_recycler.recycler

class RecyclerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler)
        var datas = arrayListOf<String>()
        for (index in 0..30) {
            datas.add("Item "+index)
        }
        val adapter = MyApapter(this, datas)
        recycler.adapter = adapter
        recycler.layoutManager = LinearLayoutManager(this)
    }
}

class MyApapter(val context: Context, val datas : List<String>)
    : Adapter<MyHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MyHolder {
        return MyHolder(LayoutInflater.from(context).inflate(R.layout.item_recycler, p0, false))
    }

    override fun getItemCount(): Int {
        return datas.size
    }

    override fun onBindViewHolder(holder: MyHolder, pos: Int) {
        holder.tvItem.text = datas[pos]
        holder.itemView.setOnClickListener {
            AlertDialog.Builder(context)
                .setTitle(datas[pos])
                .setMessage("this is dialog")
                .setNegativeButton("确定", DialogInterface.OnClickListener { dialog, which ->
                    dialog.cancel()
                })
                .show()
        }
    }
}

class MyHolder(itemView:View) : ViewHolder(itemView) {

    lateinit var tvItem : TextView

    init {
        tvItem = itemView.findViewById(R.id.tv_item)
    }

}