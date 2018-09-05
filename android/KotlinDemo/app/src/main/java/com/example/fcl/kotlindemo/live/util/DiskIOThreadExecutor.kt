package com.example.fcl.kotlindemo.live.util

import java.util.concurrent.Executor
import java.util.concurrent.Executors

/**
 * Created by galio.fang on 18-9-5
 */
class DiskIOThreadExecutor:Executor {

    private val diskIO = Executors.newSingleThreadExecutor()

    override fun execute(command: Runnable) {
        diskIO.execute(command)
    }

}
