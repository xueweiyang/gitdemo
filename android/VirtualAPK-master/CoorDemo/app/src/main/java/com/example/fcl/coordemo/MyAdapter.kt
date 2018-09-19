package com.example.fcl.coordemo

import android.support.v7.widget.RecyclerView.Adapter
import android.support.v7.widget.RecyclerView.ViewHolder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

/**
 * Created by galio.fang on 18-9-19
 */
class MyAdapter(val datas: List<String>) : Adapter<MyHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, type: Int): MyHolder {
        return MyHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.item_test,parent,false))
    }

    override fun getItemCount(): Int {
        return datas.size
    }

    override fun onBindViewHolder(holder: MyHolder, pos: Int) {
        holder.content.text = datas[pos]
    }
}

class MyHolder(itemView: View) : ViewHolder(itemView) {
    val content:TextView=itemView.findViewById(R.id.content)
}
