package com.example.andresguard

import java.io.File

object ProguardCache {
    val TAG = "ProguardCache"
    val xList = arrayListOf(
        'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
        'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'
    )
    val yList = arrayListOf(
        'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
        'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'
    )
    val zList = arrayListOf(
        'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
        'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'
    )
    /**
     * 用于混淆的名字
     */
    val nameCacheList = arrayListOf<String>()
    val dirNameCacheList = arrayListOf<String>()
    /**
     * 旧名字和混淆名字
     */
    val fileNameMap = hashMapOf<String, String>()
    val dirNameMap = hashMapOf<String, String>()

    fun init() {
        createCacheList()
        createCacheMap()
    }

    fun createCacheList() {
        Log.i(TAG, "---------create cache list--------")
        nameCacheList.clear()
        xList.forEach { x ->
            nameCacheList.add("$x")
        }
        xList.forEach { x ->
            yList.forEach { y ->
                nameCacheList.add("$x$y")
            }
        }
        xList.forEach { x ->
            yList.forEach { y ->
                zList.forEach { z ->
                    nameCacheList.add("$x$y$z")
                }
            }
        }
        dirNameCacheList.clear()
        dirNameCacheList.addAll(nameCacheList)
    }

    fun createCacheMap() {
        val dirPath = "${Constant.PROJECT_PATH}/app/src/main/res/"
//        visitFileRecursive(File(dirPath))
        File(dirPath).walk().forEach {
            if (it.isDirectory) {
                dirNameMap.put("res/${it.name}", "r/${getName(false)}")
            } else {
            }
        }
        dirNameMap.forEach {
            Log.i(TAG, "key:${it.key} value:${it.value}")
        }
    }

    fun getDir(value: String): String? {
        return dirNameMap[value]
    }

    fun getName(isFile: Boolean): String {
        if (isFile) {
            return nameCacheList.removeAt(0)
        } else {
            return dirNameCacheList.removeAt(0)
        }
    }

}