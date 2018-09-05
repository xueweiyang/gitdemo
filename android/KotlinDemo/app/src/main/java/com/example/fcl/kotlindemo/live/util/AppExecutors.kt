package com.example.fcl.kotlindemo.live.util

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executor
import java.util.concurrent.Executors

/**
 * Created by galio.fang on 18-9-5
 */
const val THREAD_COUNT=3
open class AppExecutors constructor(
    val diskIO:Executor=DiskIOThreadExecutor(),
    val networkIO:Executor=Executors.newFixedThreadPool(THREAD_COUNT),
    val mainThread:Executor=MainThreadExecutor()
) {

    private class MainThreadExecutor:Executor{

        private val mainThreadHandler= Handler(Looper.getMainLooper())

        override fun execute(command: Runnable) {
            mainThreadHandler.post(command)
        }

    }

}
