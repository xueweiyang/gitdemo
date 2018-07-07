package com.example.datepicker.adapter

import com.example.datepicker.wheel.WheelAdapter

class NumericWheelAdapter(private val minValue : Int, private val maxValue : Int)
    : WheelAdapter<Any> {

    override fun getItemsCount(): Int {
        return maxValue-minValue+1
    }

    override fun getItem(index: Int): Any {
        if (index >= 0 && index < itemsCount) {
            return minValue + index
        }
        return 0
    }

    override fun indexOf(o: Any?): Int {
        o?.let { return o as Int - minValue}
        return -1
    }
}