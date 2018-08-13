package com.example.fcl.kotlindemo

import android.util.Log
import com.example.fcl.dadademo.api.ApiService
import com.example.fcl.dadademo.util.RxSchedulerUtils
import com.example.fcl.dadademo.util.extension.subscribeRemoteData
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows
import org.robolectric.annotation.Config
import org.robolectric.shadows.ShadowLog

/**
 * Created by galio.fang on 18-8-7
 */

@RunWith(RobolectricTestRunner::class)
class ResponseTest {

    @Before
    fun setUp() {
        ShadowLog.stream = System.out
        initRxJava2()
    }

    private fun initRxJava2() {
        RxJavaPlugins.reset()
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        RxAndroidPlugins.reset()
        RxAndroidPlugins.setMainThreadSchedulerHandler { Schedulers.trampoline() }
    }

    @Test
    fun fetchHomeFoundationTest() {
        ApiService.getAdvertData(1,4)
            .compose(RxSchedulerUtils.ioToMainSchedulers())
            .subscribeRemoteData({
                println(it?.toString())
            }, {
                println(it?.errorMessage)
            })
    }
}
