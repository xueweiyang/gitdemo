package com.example.fcl.kotlindemo.save

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.fcl.kotlindemo.R

/**
 * Created by galio.fang on 18-9-6
 */
class SaveInstanceFragment : Fragment() {
    val TAG=SaveInstanceFragment::class.simpleName
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_save_instance, container, false)
    }

    override fun onStart() {
        super.onStart()
        Log.e(TAG, "on start")
    }

    override fun onStop() {
        super.onStop()
        Log.e(TAG, "on stop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e(TAG, "onDestroy")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e(TAG, "onCreate")
        retainInstance=true
    }
}
