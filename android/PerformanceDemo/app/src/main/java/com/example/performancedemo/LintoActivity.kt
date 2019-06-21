package com.example.performancedemo

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Layout
import com.facebook.litho.ComponentContext
import com.facebook.litho.LithoView
import com.facebook.litho.widget.Text

class LintoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val context = ComponentContext(this)
        val builder = Text.create(context)
        val component = builder.text("hello world")
            .textSizeDip(40f)
            .textColor(Color.parseColor("#666666"))
            .textAlignment(Layout.Alignment.ALIGN_CENTER)
            .build()
        val view = LithoView.create(context, component)
        setContentView(view)
    }
}
