package com.example.fcl.kotlindemo.live.edit

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.fcl.kotlindemo.R
import com.example.fcl.kotlindemo.databinding.AddtaskFragBinding

/**
 * Created by galio.fang on 18-9-5
 */
class AddEditTaskFragment : Fragment() {
    private lateinit var viewDataBinding: AddtaskFragBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        viewDataBinding = AddtaskFragBinding.inflate(inflater, container, false).apply {
            viewmodel = (activity as AddEditTaskActivity).obtainViewModel()
        }
        return viewDataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupFab()
        loadData()
    }

    private fun loadData() {
        viewDataBinding.viewmodel?.start(arguments?.getString(ARGUMENT_EDIT_TASK_ID))
    }

    private fun setupFab() {
        activity.findViewById<FloatingActionButton>(R.id.completeTask).apply {
            setOnClickListener {
                viewDataBinding.viewmodel?.saveTask()
            }
        }
    }

    companion object {
        const val ARGUMENT_EDIT_TASK_ID = "EDIT_TASK_ID"
        fun newInstance() = AddEditTaskFragment()
    }
}
