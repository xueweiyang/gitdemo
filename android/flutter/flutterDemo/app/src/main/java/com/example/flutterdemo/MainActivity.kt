package com.example.flutterdemo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import io.flutter.facade.Flutter

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)

        val flutterView = Flutter.createView(
            this,
            lifecycle,
            "/setting"
        )
        val layout = FrameLayout.LayoutParams(600,800)
        layout.leftMargin=100
        layout.topMargin=200
        addContentView(flutterView,layout)
    }
}
