package com.example.fcl.kotlindemo.save

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.fcl.kotlindemo.R
import com.example.fcl.kotlindemo.live.TaskActivity
import com.example.fcl.kotlindemo.live.util.replaceFragmentInActivity
import kotlinx.android.synthetic.main.activity_save_instance.editView

class SaveInstanceActivity : AppCompatActivity() {
val TAG=SaveInstanceActivity::class.simpleName
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e(TAG,"create save not null"+(savedInstanceState!=null))
        if (savedInstanceState!=null) {
            with(savedInstanceState) {
                Log.e(TAG, "create get save"+getString("content"))
            }
        }
        setContentView(R.layout.activity_save_instance)
        replaceFragmentInActivity(SaveInstanceFragment(), R.id.contentFrame)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        val text = editView.text.toString()
        outState?.apply {
//            putString("content", text)
        }
        Log.e(TAG, "on save"+text)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        Log.e(TAG,"save not null"+(savedInstanceState!=null))
        savedInstanceState?.apply {
            Log.e(TAG, "get save"+getString("content"))
        }
    }

    fun jump(view:View) {
        startActivity(Intent(this, TaskActivity::class.java))
    }
}
