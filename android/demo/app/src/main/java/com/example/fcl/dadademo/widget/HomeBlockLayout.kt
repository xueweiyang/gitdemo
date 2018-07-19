package com.example.fcl.dadademo.widget

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import com.example.fcl.dadademo.util.extension.DefaultParams

class HomeBlockLayout @JvmOverloads constructor(
    context: Context,
    attrs:AttributeSet?=null,
    defStyleAttr:Int=0
) :LinearLayout(context, attrs, defStyleAttr) {

    private val headerLayout:HomeBlockHeaderView

    var title:String
    get() = headerLayout.title
    set(value) {
        headerLayout.title=value
    }

    init {
        orientation=LinearLayout.VERTICAL

        headerLayout= HomeBlockHeaderView(context)
        val params=LayoutParams(
            DefaultParams.MATCH_PARENT,
            DefaultParams.WRAP_CONTENT
        )
        addView(headerLayout,params)
    }

}