package com.example.fcl.kotlindemo.live

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.databinding.ObservableArrayList
import android.databinding.ObservableList
import com.example.fcl.kotlindemo.live.data.Task
import com.example.fcl.kotlindemo.live.data.TaskDataSource
import com.example.fcl.kotlindemo.live.data.TaskRepository

/**
 * Created by galio.fang on 18-9-4
 */
class TaskViewModel(
    context:Application,
    private val taskRepository:TaskRepository
) : AndroidViewModel(context) {

    internal val openTaskEvent=SingleLiveEvent<String>()
    val newTaskEvent=SingleLiveEvent<Void>()
    val items:ObservableList<Task> = ObservableArrayList()

    fun addNewTask() {
        newTaskEvent.call()
    }

    fun start() {
        loadTasks(false)
    }

    fun loadTasks(forceUpdate:Boolean) {
        loadTasks(forceUpdate,true)
    }

    fun loadTasks(forceUpdate: Boolean,showLoadingUI:Boolean) {
        taskRepository.getTasks(object :TaskDataSource.LoadTasksCallback{

            override fun onTasksLoaded(tasks: List<Task>) {
                with(items) {
                    clear()
                    addAll(tasks)
                }
            }

            override fun onDataNotAvailable() {

            }

        })
    }

}
