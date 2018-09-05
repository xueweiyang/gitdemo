package com.example.fcl.kotlindemo.live

import android.databinding.DataBindingUtil
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.fcl.kotlindemo.databinding.TaskItemBinding
import com.example.fcl.kotlindemo.live.data.Task

/**
 * Created by galio.fang on 18-9-5
 */
class TasksAdapter(
    private var tasks: List<Task>,
    private val tasksViewModel: TaskViewModel
) : BaseAdapter() {

    override fun getCount(): Int {
        return tasks.size
    }

    override fun getItem(position: Int): Any {
        return tasks[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val inflater = LayoutInflater.from(parent.context)
        val binding = TaskItemBinding.inflate(inflater, parent, false)
        with(binding) {
            task = tasks[position]
            executePendingBindings()
        }
        return binding.root
    }

    fun replaceData(tasks: List<Task>) {
        setList(tasks)
    }

    private fun setList(tasks: List<Task>) {
        this.tasks = tasks
        notifyDataSetChanged()
    }
}
