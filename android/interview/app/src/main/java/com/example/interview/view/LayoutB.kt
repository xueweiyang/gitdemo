package com.example.interview.view

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.widget.LinearLayout

class LayoutB @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    val TAG = "LayoutB"

    override fun onTouchEvent(event: MotionEvent): Boolean {
        Log.e(TAG, "ontouch:${event.action}")
        return super.onTouchEvent(event)
    }

}