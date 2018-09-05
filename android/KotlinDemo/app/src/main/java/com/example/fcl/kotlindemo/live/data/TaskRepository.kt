package com.example.fcl.kotlindemo.live.data

import com.example.fcl.kotlindemo.live.data.TaskDataSource.GetTaskCallback
import com.example.fcl.kotlindemo.live.data.TaskDataSource.LoadTasksCallback
import java.util.LinkedHashMap

/**
 * Created by galio.fang on 18-9-4
 */
class TaskRepository(
    val tasksRemoteDataSource:TaskDataSource,
    val tasksLocalDataSource:TaskDataSource
) : TaskDataSource {

    var cachedTasks: LinkedHashMap<String, Task> = LinkedHashMap()

    override fun getTasks(callback: LoadTasksCallback) {
        tasksLocalDataSource.getTasks(object :TaskDataSource.LoadTasksCallback{
            override fun onTasksLoaded(tasks: List<Task>) {
                callback.onTasksLoaded(tasks)
            }

            override fun onDataNotAvailable() {

            }
        })
    }

    override fun getTask(taskId: String, callback: GetTaskCallback) {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun saveTask(task: Task) {
        cacheAndPerform(task) {
            tasksRemoteDataSource.saveTask(it)
            tasksLocalDataSource.saveTask(it)
        }
    }

    private inline fun cacheAndPerform(task:Task,perform:(Task)->Unit) {
        val cachedTask = Task(task.title,task.description,task.id).apply {
            isCompleted = task.isCompleted
        }
        cachedTasks.put(cachedTask.id, cachedTask)
        perform(cachedTask)
    }

    companion object {
        private var INSTANCE:TaskRepository?=null

        @JvmStatic fun getInstance(tasksRemoteDataSource: TaskDataSource,
            tasksLocalDataSource: TaskDataSource) =
            INSTANCE ?: synchronized(TaskRepository::class.java) {
                INSTANCE?: TaskRepository(tasksRemoteDataSource,tasksLocalDataSource)
                    .also { INSTANCE=it }
            }

        @JvmStatic fun destroyInstance() {
            INSTANCE=null
        }
    }
}
