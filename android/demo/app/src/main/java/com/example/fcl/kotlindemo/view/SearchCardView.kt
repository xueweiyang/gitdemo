package com.example.fcl.kotlindemo.view

import android.content.Context
import android.util.AttributeSet
import android.widget.EditText
import android.widget.FrameLayout
import com.example.fcl.kotlindemo.R
import com.example.fcl.kotlindemo.extension.inflateLayout
import kotlinx.android.synthetic.main.search_card.view.searchClear
import kotlinx.android.synthetic.main.search_card.view.searchEt
import java.util.jar.Attributes

class SearchCardView constructor(context: Context?, attrs : AttributeSet?)
    : FrameLayout(context, attrs) {

    init {
        context?.inflateLayout(R.layout.search_card,this, true)
        searchClear.setOnClickListener{ searchEt.text = null }
    }

    fun getEt() : EditText = searchEt
}