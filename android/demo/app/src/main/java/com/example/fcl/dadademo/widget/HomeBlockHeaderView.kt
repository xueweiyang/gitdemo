package com.example.fcl.dadademo.widget

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.AppCompatTextView
import android.util.AttributeSet
import android.view.Gravity
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import android.widget.FrameLayout
import android.widget.TextView
import com.example.fcl.dadademo.util.extension.dpToPx
import com.example.fcl.kotlindemo.R

class HomeBlockHeaderView @JvmOverloads constructor(
    context: Context,
    attrs:AttributeSet?=null,
    defStyleAttr:Int=0
) :FrameLayout(context, attrs, defStyleAttr) {

    private val titleView:TextView
    val moreView:TextView

    var title:String
    get() = titleView.text.toString()
    set(value) {
        titleView.text=value
    }

    init {
        val padding=context.dpToPx(20f)
        val paddingTop=context.dpToPx(24f)
        val paddingBottom=context.dpToPx(16f)
        setPadding(padding.toInt(),paddingTop.toInt(),padding.toInt(),paddingBottom.toInt())

        titleView=AppCompatTextView(context)
        titleView.textSize=20f
        titleView.setTextColor(ContextCompat.getColor(context, R.color.dada_black_4))
        addView(titleView)

        moreView=AppCompatTextView(context)
        moreView.textSize=14f
        moreView.setText("更多")
        moreView.setTextColor(ContextCompat.getColor(context,R.color.dada_grey_19))
        val params=FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT)
        params.gravity=Gravity.END or Gravity.CENTER_VERTICAL
        addView(moreView,params)
    }

}