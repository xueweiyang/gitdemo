package com.example.fcl.kotlindemo.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.ViewHolder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.fcl.kotlindemo.R
import com.example.fcl.kotlindemo.adapter.ResultAdapter.ResultHolder
import com.example.fcl.kotlindemo.model.Result

class ResultAdapter(val context: Context) : RecyclerView.Adapter<ResultHolder>() {

    private var resultList : List<Result> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.item_result, parent, false)
        return ResultHolder(itemView)
    }

    override fun getItemCount(): Int {
        return resultList.size
    }

    fun setData(resultList : List<Result>) {
        this.resultList = resultList
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ResultHolder, pos: Int) {
        holder.titleTv.text = resultList.get(pos).name
    }

    class ResultHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val titleTv : TextView = itemView.findViewById(R.id.title)
    }
}