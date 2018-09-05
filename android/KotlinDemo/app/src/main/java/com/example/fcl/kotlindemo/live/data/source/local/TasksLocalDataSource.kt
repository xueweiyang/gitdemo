package com.example.fcl.kotlindemo.live.data.source.local

import android.support.annotation.VisibleForTesting
import com.example.fcl.kotlindemo.live.data.Task
import com.example.fcl.kotlindemo.live.data.TaskDataSource
import com.example.fcl.kotlindemo.live.data.TaskDataSource.GetTaskCallback
import com.example.fcl.kotlindemo.live.data.TaskDataSource.LoadTasksCallback
import com.example.fcl.kotlindemo.live.util.AppExecutors

/**
 * Created by galio.fang on 18-9-5
 */
class TasksLocalDataSource private constructor(
    val appExecutors:AppExecutors,
    val tasksDao:TasksDao
):TaskDataSource{
    override fun getTasks(callback: LoadTasksCallback) {
        appExecutors.diskIO.execute {
            val tasks=tasksDao.getTasks()
            appExecutors.mainThread.execute {
                if (tasks.isEmpty()) {
                    callback.onDataNotAvailable()
                } else{
                    callback.onTasksLoaded(tasks)
                }
            }
        }
    }

    override fun getTask(taskId: String, callback: GetTaskCallback) {
        appExecutors.diskIO.execute {
            val task = tasksDao.getTaskById(taskId)
            appExecutors.mainThread.execute {
                if (task!=null) {
                    callback.onTaskLoaded(task)
                } else{
                    callback.onDataNotAvailable()
                }
            }
        }
    }

    override fun saveTask(task: Task) {
        appExecutors.diskIO.execute {
            tasksDao.insertTask(task)
        }
    }

    companion object {
        private var INSTANCE: TasksLocalDataSource? = null

        @JvmStatic
        fun getInstance(appExecutors: AppExecutors, tasksDao: TasksDao): TasksLocalDataSource {
            if (INSTANCE == null) {
                synchronized(TasksLocalDataSource::javaClass) {
                    INSTANCE = TasksLocalDataSource(appExecutors, tasksDao)
                }
            }
            return INSTANCE!!
        }

        @VisibleForTesting
        fun clearInstance() {
            INSTANCE = null
        }
    }
}
