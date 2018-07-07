package com.example.fcl.kotlindemo.extension

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

fun Context.inflateLayout(resource: Int, root: ViewGroup? = null, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(this).inflate(resource, root, attachToRoot)
}