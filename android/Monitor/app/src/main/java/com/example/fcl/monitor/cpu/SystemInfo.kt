package com.example.fcl.monitor.cpu

import java.io.Closeable
import java.lang.Exception

/**
 * Created by galio.fang on 19-4-2
 */
class SystemInfo {

    fun closeQuietly(closeable: Closeable?) {
        try {
            closeable?.close()
        } catch (e:Exception) {

        }
    }

}
