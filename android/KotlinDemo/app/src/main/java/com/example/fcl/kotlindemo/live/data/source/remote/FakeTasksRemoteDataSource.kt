package com.example.fcl.kotlindemo.live.data.source.remote

import com.example.fcl.kotlindemo.live.data.Task
import com.example.fcl.kotlindemo.live.data.TaskDataSource
import com.example.fcl.kotlindemo.live.data.TaskDataSource.GetTaskCallback
import com.example.fcl.kotlindemo.live.data.TaskDataSource.LoadTasksCallback
import com.google.common.collect.Lists
import java.util.LinkedHashMap

/**
 * Created by galio.fang on 18-9-5
 */
object FakeTasksRemoteDataSource:TaskDataSource{
    private var TASKS_SERVICE_DATA: LinkedHashMap<String, Task> = LinkedHashMap()

    override fun getTasks(callback: LoadTasksCallback) {
        callback.onTasksLoaded(Lists.newArrayList(TASKS_SERVICE_DATA.values))
    }

    override fun getTask(taskId: String, callback: GetTaskCallback) {
     val task = TASKS_SERVICE_DATA[taskId]
        task?.let { callback.onTaskLoaded(it) }
    }

    override fun saveTask(task: Task) {
        TASKS_SERVICE_DATA.put(task.id,task)
    }
}
