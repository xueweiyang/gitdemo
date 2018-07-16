package com.example.fcl.dadademo.util

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.fcl.kotlindemo.MyApp

object ImageLoader{

    fun loadImage(url:String, imageView: ImageView, context: Context) {
        Glide.with(context)
            .load(url)
            .into(imageView)
    }

}