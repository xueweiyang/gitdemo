package com.example.fcl.kotlindemo.live

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.fcl.kotlindemo.databinding.TaskFragBinding

/**
 * Created by galio.fang on 18-9-4
 */
class TaskFragment : Fragment() {

    private lateinit var viewDataBinding : TaskFragBinding


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewDataBinding = TaskFragBinding.inflate(inflater, container, false).apply {
            viewmodel = (activity as TaskActivity)
        }
        return super.onCreateView(inflater, container, savedInstanceState)
    }

}
