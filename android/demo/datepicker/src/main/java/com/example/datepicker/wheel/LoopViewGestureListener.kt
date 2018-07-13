package com.example.datepicker.wheel

import android.view.MotionEvent

/**
 * 手势监听
 */
class LoopViewGestureListener(private val wheelView: WheelView) :
    android.view.GestureDetector.SimpleOnGestureListener() {

    override fun onFling(e1: MotionEvent, e2: MotionEvent, velocityX: Float, velocityY: Float): Boolean {
        wheelView.scrollBy(velocityY)
        return true
    }
}
