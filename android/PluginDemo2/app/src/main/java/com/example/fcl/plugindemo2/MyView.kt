package com.example.fcl.plugindemo2

import android.content.Context
import android.util.AttributeSet
import android.view.View

/**
 * Created by galio.fang on 18-10-26
 */
class MyView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet?=null
) : View(context, attributeSet) {
    init {
        System.out.println(HotPatchApplication::class.java)
    }
}
