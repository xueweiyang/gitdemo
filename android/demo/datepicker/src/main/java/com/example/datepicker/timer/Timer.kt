package com.example.datepicker.timer

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.example.datepicker.R
import com.example.datepicker.wheel.OnItemSelectedListener
import com.example.datepicker.wheel.WheelView

class Timer : LinearLayout {
    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        LayoutInflater.from(context).inflate(R.layout.layout_timer, this)
        val yearWheel = findViewById<WheelView>(R.id.dayWheel)
        yearWheel.setOnItemSelectedListener(object : OnItemSelectedListener {
            override fun onItemSelected(index: Int) {
            }
        })
    }
}
