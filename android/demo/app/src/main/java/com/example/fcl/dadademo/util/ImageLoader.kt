package com.example.fcl.dadademo.util

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.fcl.kotlindemo.MyApp

object ImageLoader{

    fun loadImage(url:String, imageView: ImageView) {
        Glide.with(MyApp.context)
            .load(url)
            .into(imageView)
    }

}