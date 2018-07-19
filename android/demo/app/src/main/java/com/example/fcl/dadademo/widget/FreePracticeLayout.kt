package com.example.fcl.dadademo.widget

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.AppCompatImageView
import android.support.v7.widget.AppCompatTextView
import android.util.AttributeSet
import com.example.fcl.dadademo.model.FreePracticeItem
import com.example.fcl.kotlindemo.R

class FreePracticeLayout @JvmOverloads constructor(
    context: Context,
    attrs:AttributeSet?=null,
    defStyleAttr:Int=0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var freePracticeCover:AppCompatImageView?=null
    private var freePracticeName:AppCompatTextView?=null

    override fun onFinishInflate() {
        super.onFinishInflate()
        freePracticeCover = findViewById(R.id.freePracticeCover)
        freePracticeName=findViewById(R.id.freePracticeName)
    }

    fun setupFreePractice(freePracticeItem: FreePracticeItem) {
        if ("有声绘本".equals(freePracticeItem.itemName)) {
            freePracticeCover?.setBackgroundResource(R.mipmap.ic_free_practice_audio_book)
        } else if ("口语练习".equals(freePracticeItem.itemName)) {
            freePracticeCover?.setBackgroundResource(R.mipmap.ic_free_practice_evaluate)
        }
        freePracticeName?.text=freePracticeItem.itemName
    }

}