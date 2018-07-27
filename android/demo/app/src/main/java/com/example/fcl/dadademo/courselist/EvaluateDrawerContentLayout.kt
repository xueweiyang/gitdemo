package com.example.fcl.dadademo.courselist

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import com.example.fcl.kotlindemo.R

class EvaluateDrawerContentLayout @JvmOverloads constructor(
    context: Context,
    attrs:AttributeSet?=null,
    defInt:Int=0
) : RelativeLayout(context, attrs, defInt) {

    init {
        LayoutInflater.from(context).inflate(R.layout.layout_evaluate_drawer_content, this, true)
    }

}