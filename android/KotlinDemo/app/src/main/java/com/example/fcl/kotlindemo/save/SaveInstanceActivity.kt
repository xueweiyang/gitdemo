package com.example.fcl.kotlindemo.save

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.fcl.kotlindemo.R
import kotlinx.android.synthetic.main.activity_save_instance.editView

class SaveInstanceActivity : AppCompatActivity() {
val TAG=SaveInstanceActivity::class.simpleName
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_save_instance)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        val text = editView.text.toString()
        outState.apply {
            putString("content", text)
        }
        Log.e(TAG, "on save"+text)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)

    }
}
