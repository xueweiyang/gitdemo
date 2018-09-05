package com.example.fcl.kotlindemo.live.edit

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import com.example.fcl.kotlindemo.live.data.Task
import com.example.fcl.kotlindemo.live.data.TaskRepository
import com.example.fcl.kotlindemo.live.util.ToastHelper

/**
 * Created by galio.fang on 18-9-5
 */
class AddEditTaskViewModel(
    val context:Application,
    private val taskRepository: TaskRepository
) :AndroidViewModel(context) {

    val title=ObservableField<String>()
    val desc=ObservableField<String>()
    val dataLoading=ObservableBoolean(false)
    private var taskId:String?=null
    private val isNewTask
        get() = taskId==null

    fun start(taskId:String?) {
        if (dataLoading.get()) {
            return
        }
        this.taskId=taskId
    }


    fun saveTask() {
        val task = Task(title.get()?:"", desc.get()?:"")
        if (task.isEmpty) {
            ToastHelper.toast(context,"cannot be empty")
            return
        }
        if (isNewTask) {
            createTask(task)
        }
    }

    private fun createTask(task: Task) {
        taskRepository.saveTask(task)
    }
}
