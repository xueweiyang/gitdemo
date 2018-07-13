package com.example.fcl.dadademo.util

import android.util.Log
import android.widget.Toast
import com.example.fcl.kotlindemo.MyApp

object ToastHelper{

    fun toast(message:String){
//        Toast.makeText(MyApp().getContext(), message, Toast.LENGTH_SHORT).show
        Log.e("ToastHelper", message)
    }

}