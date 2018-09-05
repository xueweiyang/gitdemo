package com.example.fcl.kotlindemo.live.util

import android.content.Context
import android.widget.Toast

/**
 * Created by galio.fang on 18-9-5
 */
object ToastHelper{

    fun toast(context:Context,value:String) {
        Toast.makeText(context,value,Toast.LENGTH_SHORT).show()
    }
}
