package com.example.fcl.dadademo.util

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.fcl.kotlindemo.MyApp

object ImageLoader{

    fun loadImage(url:String, imageView: ImageView) {
        Glide.with(MyApp.instance)
            .load(url)
            .into(imageView)
    }
    fun loadImage(url:Int, imageView: ImageView) {
        Glide.with(MyApp.instance)
            .load(url)
            .into(imageView)
    }
}