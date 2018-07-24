package com.example.fcl.dadademo.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import com.example.fcl.dadademo.model.FreePracticeItem
import com.example.fcl.kotlindemo.R
import com.google.android.flexbox.FlexboxLayout

class HomeFreePracticeLayout @JvmOverloads constructor(
    context: Context,
    attrs:AttributeSet?=null,
    defStyleAttr:Int=0
) : FlexboxLayout(context, attrs, defStyleAttr) {

    var freePracticeClickCallback:HomeFreePracticeClickCallback?=null

    fun setupFreePractice(items:List<FreePracticeItem>) {
        removeAllViews()
        items.take(2).forEach { item->
            val freePracticeView = LayoutInflater.from(context).inflate(
                R.layout.layout_free_practice,
                this,
                false
            ) as FreePracticeLayout

            addView(freePracticeView)
            freePracticeView.setupFreePractice(item)
            freePracticeView.setOnClickListener {
                freePracticeClickCallback?.invoke(item)
            }
        }
    }

}
typealias HomeFreePracticeClickCallback = ((FreePracticeItem)-> Unit)