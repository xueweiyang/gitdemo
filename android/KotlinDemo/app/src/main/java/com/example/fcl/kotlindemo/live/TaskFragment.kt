package com.example.fcl.kotlindemo.live

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.fcl.kotlindemo.R
import com.example.fcl.kotlindemo.databinding.TaskFragBinding
import kotlinx.android.synthetic.main.activity_task.view.addTask
import kotlinx.android.synthetic.main.task_frag.taskList

/**
 * Created by galio.fang on 18-9-4
 */
class TaskFragment : Fragment() {

    private lateinit var viewDataBinding : TaskFragBinding
    lateinit var listAdapter:TasksAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewDataBinding = TaskFragBinding.inflate(inflater, container, false).apply {
            viewmodel = (activity as TaskActivity).obtainViewModel()
        }
        return viewDataBinding.root
    }

    override fun onResume() {
        super.onResume()
        viewDataBinding.viewmodel?.start()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupFab()
        setupListAdapter()
    }

    private fun setupListAdapter() {
        val viewModel=viewDataBinding.viewmodel
        if (viewModel!=null){
            listAdapter = TasksAdapter(ArrayList(0), viewModel)
            taskList.adapter=listAdapter
        }
    }

    private fun setupFab() {
        activity.findViewById<FloatingActionButton>(R.id.addTask).run {
            setOnClickListener {
                viewDataBinding.viewmodel?.addNewTask()
            }
        }
    }
}
