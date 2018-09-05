package com.example.fcl.kotlindemo.live.data

/**
 * Created by galio.fang on 18-9-4
 */
interface TaskDataSource {

    interface LoadTasksCallback {
        fun onTasksLoaded(tasks:List<Task>)
        fun onDataNotAvailable()
    }

    interface GetTaskCallback{
        fun onTaskLoaded(task: Task)
        fun onDataNotAvailable()
    }

    fun getTasks(callback:LoadTasksCallback)

    fun getTask(taskId:String,callback: GetTaskCallback)

    fun saveTask(task: Task)
}
