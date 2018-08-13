package com.example.fcl.kotlindemo

import io.reactivex.Scheduler
import io.reactivex.functions.Function
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.runners.model.Statement

/**
 * Created by galio.fang on 18-8-7
 */
class Test {

    internal fun te() {
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
    }

    fun a(): Statement {
        return object : Statement() {
            override fun evaluate() {
            }
        }
    }
}
