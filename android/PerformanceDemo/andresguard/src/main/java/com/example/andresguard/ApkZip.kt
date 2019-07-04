package com.example.andresguard

import com.example.andresguard.util.unZipTo
import java.io.File

/**
 * 解压apk，获取resources.arsc文件
 */
object ApkZip{

    val TAG = "ApkZip"

    fun unzip() {
        Log.i(TAG, "----------unzip apk--------")
        val apkPath = "${Constant.PROJECT_PATH}/app/build/outputs/apk/release/app-release-unsigned.apk"
        val apkZipFile = File(apkPath.replace(".apk", ".zip"))
        File(apkPath).renameTo(apkZipFile)
        apkZipFile.unZipTo(apkPath.replace(".apk", ""))
    }

}