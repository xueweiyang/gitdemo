package com.example.resourceanay

import android.graphics.Bitmap

class DumplicatedCollectInfo constructor(
    val hash: String
) {

    val bitmapInstances = arrayListOf<BitmapInstance>()
    var duplicatedCount = 0
    var width = 0
    var height = 0
    var size = 0

    fun addBitmapInstance(bitmapInstance: BitmapInstance) {
        bitmapInstances.add(bitmapInstance)
    }

    fun internalSetValue() {
        duplicatedCount = bitmapInstances.size
        if (duplicatedCount > 0) {
            val instance = bitmapInstances[0]
            width = instance.width
            height = instance.height
            size = instance.size
        }
    }

    fun string() :String{
        val builder = StringBuilder()
        builder.append("{\n\t\"hash\":${hash},\n" +
                "\t\"size\":${size},\n" +
                "\t\"width\":${width},\n" +
                "\t\"height\":${height},\n" +
                "\t\"duplicatedCount\":${duplicatedCount},\n" +
                "\t\"stack\":[\n")
        bitmapInstances.forEachIndexed { index, bitmapInstance ->
            builder.append(bitmapInstance.getTraceFromLeakCanary())
            if (index != bitmapInstances.size-1){
                builder.append(",\n")
            }
        }
        builder.append("]\n}\n")
        return builder.toString()
    }
}