package com.example.fcl.kotlindemo

import com.github.moduth.blockcanary.BlockCanaryContext

class MyBlockCanaryContext : BlockCanaryContext() {

    override fun provideQualifier(): String {
        val packgeInfo = MyApp.instance.packageManager.getPackageInfo(MyApp.instance.packageName, 0)
        var qualifier =packgeInfo.versionCode.toString()+"_"+packgeInfo.versionName
        return qualifier
    }

    override fun provideBlockThreshold(): Int {
        return 500
    }

    override fun displayNotification(): Boolean {
        return BuildConfig.DEBUG
    }

    override fun stopWhenDebugging(): Boolean {
        return false
    }

}