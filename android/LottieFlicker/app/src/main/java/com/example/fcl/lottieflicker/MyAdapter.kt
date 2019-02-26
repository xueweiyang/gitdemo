package com.example.fcl.lottieflicker

import android.support.v7.widget.RecyclerView.Adapter
import android.support.v7.widget.RecyclerView.ViewHolder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_test.view.contentView
import kotlinx.android.synthetic.main.item_test.view.lottieView

/**
 * Created by galio.fang on 19-2-15
 */
class MyAdapter(val items: List<Boolean>) : Adapter<MyHolder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MyHolder {
        val view = LayoutInflater.from(p0.context).inflate(R.layout.item_test, p0, false)
        return MyHolder(view = view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: MyHolder, p1: Int) {

        holder.lottieView.speed = 0f
        holder.lottieView.progress = if (items[p1]) 1f else 0f
        holder.contentView.text = if (items[p1]) "点赞" else "未点赞"
    }
}

class MyHolder constructor(view: View) : ViewHolder(view) {
    val lottieView = view.lottieView
    val contentView = view.contentView

    init {
        lottieView.setAnimation("audio_book_like.json")
    }
}
