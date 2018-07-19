package com.example.fcl.dadademo.util

import com.example.fcl.dadademo.model.SplashAd
import com.example.fcl.kotlindemo.MyApp

object CacheManager{
private val cachePreference:CachePreference= CachePreference(MyApp.instance)

    fun saveSplashAdCache(splashAd: SplashAd) {
        cachePreference.saveSplashData(splashAd)
    }

    fun loadSplashAdCache() :SplashAd?{
        return cachePreference.loadSplashAdData()
    }
}