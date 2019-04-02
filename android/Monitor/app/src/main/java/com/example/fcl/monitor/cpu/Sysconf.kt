package com.example.fcl.monitor.cpu

import android.annotation.SuppressLint
import android.os.Build
import android.system.Os
import android.system.OsConstants
import android.util.Log
import java.lang.Exception

/**
 * Created by galio.fang on 19-4-2
 */
object Sysconf {

    const val TAG = "Sysconf"

    private const val DEFAULT_CLOCK_TICKS_PER_SECOND = 100L

    fun getScClkTck(): Long {
        return getScClkTck(DEFAULT_CLOCK_TICKS_PER_SECOND)
    }

    @SuppressLint("ObsoleteSdkInt")
    fun getScClkTck(fallback: Long): Long {
        val result = when {
            Build.VERSION.SDK_INT >= 21 -> Os.sysconf(OsConstants._SC_CLK_TCK)
            Build.VERSION.SDK_INT >= 14 -> fromLibcore("_SC_NPROCESSORS_CONF", fallback)
            else -> fallback
        }
        return if (result > 0) {
            result
        } else {
            fallback
        }
    }

    fun fromLibcore(field: String, fallback: Long): Long {
        try {
            val osConstantsClass = Class.forName("libcore.io.OsConstants")
            val scClkTck = osConstantsClass.getField(field)
            val libcoreClass = Class.forName("libcore.io.Libcore")
            val osClass = Class.forName("libcore.io.Os")
            val osInstance = libcoreClass.getField("os").get(null)
            return osClass.getMethod("sysconf", Int.javaClass).invoke(osInstance, scClkTck) as Long
        } catch (e: Exception) {
            logReflectionException(e)
        }
        return fallback
    }

    fun logReflectionException(ex: Exception) {
        Log.e(TAG, "Unable to read _SC_CLK_TCK by reflection", ex)
    }
}
