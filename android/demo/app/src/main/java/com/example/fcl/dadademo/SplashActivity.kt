package com.example.fcl.dadademo

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.fcl.dadademo.api.ApiService
import com.example.fcl.dadademo.util.CacheManager
import com.example.fcl.dadademo.util.ImageLoader
import com.example.fcl.dadademo.util.RxSchedulerUtils
import com.example.fcl.dadademo.util.ToastHelper
import com.example.fcl.dadademo.util.extension.realImageUrl
import com.example.fcl.dadademo.util.extension.subscribeRemoteData
import com.example.fcl.kotlindemo.R
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_splash.splashAdImg
import kotlinx.android.synthetic.main.activity_splash.splashText
import java.util.concurrent.TimeUnit.SECONDS

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hideStatusBar()
        setContentView(R.layout.activity_splash)
        initDate()
        splashText.setOnClickListener {
            navigationNext()
        }
    }

    private fun hideStatusBar() {
        val decorView = window.decorView
        val uiOptions = (View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            or View.SYSTEM_UI_FLAG_FULLSCREEN)
        decorView.systemUiVisibility=uiOptions
    }

    private fun initDate() {
        downSplashData()
        Observable.timer(1, SECONDS)
            .subscribe({
                                navigationNext()
            })
//        val loadSplashAdCache = CacheManager.loadSplashAdCache()
//        var imgUrl=""
//        loadSplashAdCache?.let { adData ->
//            val curTime = System.currentTimeMillis() / 1000
//            if (adData.startTime <= curTime && curTime <= adData.endTime) {
//                imgUrl=adData.imageUrl
//            }
//        }
//        ImageLoader.loadImage(imgUrl, splashAdImg)
    }

    private fun downSplashData() {
        ApiService.getAdvertData(1, 4)
            .compose(RxSchedulerUtils.ioToMainSchedulers())
            .subscribeRemoteData({ model ->
                model?.let {
                    if (it.adList.isNotEmpty()) {
                        Log.e("Splash", it.adList[0].imageUrl)
                        val imgUrl = it.adList[0].imageUrl.realImageUrl()
//                        CacheManager.saveSplashAdCache(it.adList[0])
                        ImageLoader.loadImage(imgUrl, splashAdImg)
                    }
                }
            }, {
                it?.printStackTrace()
            })
    }

    private fun navigationNext() {
        startActivity(Intent(this, DadaMainActivity::class.java))
        finish()
    }
}
