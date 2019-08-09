package com.example.interview.view

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.LinearLayout

class ViewA @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    val TAG = "ViewA"

    init {
        setOnClickListener {
            Log.e(TAG, "click")
        }
        isClickable = true
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        Log.e(TAG, "ontouch:${event.action}")
        return super.onTouchEvent(event)
    }


}