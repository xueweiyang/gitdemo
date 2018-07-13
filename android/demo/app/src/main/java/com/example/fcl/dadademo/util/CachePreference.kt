package com.example.fcl.dadademo.util

import android.content.Context
import com.example.fcl.dadademo.model.SplashAd
import com.google.gson.Gson

class CachePreference(context:Context):BasePrefrerenceRepository(context) {

    override val name: String
        get() = "cache"

    fun saveSplashData(data:SplashAd) {
        setString(SPLASH_DATA, Gson().toJson(data))
    }

    fun loadSplashAdData() :SplashAd?{
        val dataString = getString(SPLASH_DATA)
        if (dataString.isEmpty()) {
            return null
        }
        return Gson().fromJson(dataString,SplashAd::class.java)
    }

    companion object {
        const val SPLASH_DATA = "splash_data"
    }
}