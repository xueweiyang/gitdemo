package com.example.fcl.dadademo.util.extension

import android.content.Context
import android.util.TypedValue
import android.view.ViewGroup

typealias DefaultParams = ViewGroup.LayoutParams

fun Context.dpToPx(dp: Float) =
    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.displayMetrics)

fun Context.dpToPx(dp: Int): Int =
    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), resources.displayMetrics).toInt()