package com.example.fcl.scrolldemo.datepicker

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.example.fcl.scrolldemo.R

class TimerView constructor(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {

    init {
        LayoutInflater.from(context).inflate(R.layout.layout_timer, this)
    }

}