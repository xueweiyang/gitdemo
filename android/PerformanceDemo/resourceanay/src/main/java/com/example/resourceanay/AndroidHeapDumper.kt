package com.example.resourceanay

import android.os.Debug
import android.os.Environment
import java.io.File

class AndroidHeapDumper {

    fun dumpHeap(): File {
        val dir = Environment.getExternalStorageDirectory().absolutePath + "/dump"
        File(dir).mkdirs()
        val path = "$dir/dump1.hprof"
        val file = File(path)
        Debug.dumpHprofData(path)
        return file
    }

}