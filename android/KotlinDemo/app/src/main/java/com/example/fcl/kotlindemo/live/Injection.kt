package com.example.fcl.kotlindemo.live

import android.content.Context
import com.example.fcl.kotlindemo.live.data.TaskRepository
import com.example.fcl.kotlindemo.live.data.source.local.TasksLocalDataSource
import com.example.fcl.kotlindemo.live.data.source.local.ToDoDatabase
import com.example.fcl.kotlindemo.live.data.source.remote.FakeTasksRemoteDataSource
import com.example.fcl.kotlindemo.live.util.AppExecutors

/**
 * Created by galio.fang on 18-9-5
 */
object Injection {

    fun provideTasksRepository(context: Context) :TaskRepository{

        val database = ToDoDatabase.getInstance(context)
        return TaskRepository.getInstance(FakeTasksRemoteDataSource,
            TasksLocalDataSource.getInstance(AppExecutors(),database.taskDao()))

    }

}
