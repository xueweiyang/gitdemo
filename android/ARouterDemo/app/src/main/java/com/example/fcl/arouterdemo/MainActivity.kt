package com.example.fcl.arouterdemo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.callback.NavCallback
import com.alibaba.android.arouter.launcher.ARouter

class MainActivity : AppCompatActivity() {
    val TAG="MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun click(view: View) {
        ARouter.getInstance().build("/com/Main2Activity")
            .navigation(this, object : NavCallback() {
                override fun onArrival(postcard: Postcard?) {
                    Log.e(TAG, "onArrival")
                }

                override fun onLost(postcard: Postcard?) {
                    super.onLost(postcard)
                    Log.e(TAG, "onLost")
                }
            })
    }

}
