package com.example.fcl.dadademo.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.View
import com.example.fcl.kotlindemo.R

private const val WIDTH = 5.0f
private const val BOTTOM = 5.0f

class WebViewProgressBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    lateinit var paint: Paint

    var progress = 1
        set(value) {
            progress = value
            invalidate()
        }

    init {
        paint = Paint(Paint.ANTI_ALIAS_FLAG or Paint.DITHER_FLAG)
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = WIDTH
        paint.color = ContextCompat.getColor(context, R.color.colorPrimary)
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawRect(0f, 0f, (width * progress / 100).toFloat(), BOTTOM, paint)
    }
}