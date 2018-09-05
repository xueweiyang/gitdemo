package com.example.fcl.kotlindemo.live

import android.app.Application
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.support.annotation.VisibleForTesting
import com.example.fcl.kotlindemo.live.data.TaskRepository
import com.example.fcl.kotlindemo.live.edit.AddEditTaskViewModel

/**
 * Created by galio.fang on 18-9-4
 */
class ViewModelFactory private constructor(
    private val application:Application,
    private val tasksRepository:TaskRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>)=
        with(modelClass) {
            when {
                isAssignableFrom(TaskViewModel::class.java)->
                    TaskViewModel(application,tasksRepository)
                isAssignableFrom(AddEditTaskViewModel::class.java)->
                    AddEditTaskViewModel(application,tasksRepository)
                else->
                    throw IllegalArgumentException("Unknown ViewModel class:{${modelClass.name}}")
            }
        } as T

    companion object {

        @Volatile private var INSTANCE :ViewModelFactory?=null

        fun getInstance(application: Application) =
            INSTANCE ?: synchronized(ViewModelFactory::class.java) {
                INSTANCE ?: ViewModelFactory(application,
                    Injection.provideTasksRepository(application.applicationContext))
                    .also { INSTANCE=it }
            }

        @VisibleForTesting fun destroyInstance() {
            INSTANCE=null
        }

    }

}
