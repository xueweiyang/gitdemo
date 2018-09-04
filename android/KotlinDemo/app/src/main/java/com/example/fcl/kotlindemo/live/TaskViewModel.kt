package com.example.fcl.kotlindemo.live

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import com.example.fcl.kotlindemo.live.data.TaskRepository

/**
 * Created by galio.fang on 18-9-4
 */
class TaskViewModel(
    context:Application,
    private val taskRepository:TaskRepository
) : AndroidViewModel(context) {



}
