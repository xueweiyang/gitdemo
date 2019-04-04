package com.example.fcl.monitor.cpu

import android.os.SystemClock
import android.text.TextUtils
import android.util.Log
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.io.RandomAccessFile
import java.lang.Exception
import java.util.Collections

/**
 * Created by galio.fang on 19-4-2
 */
class ProcessCpuTracker(processId: Int) {

    companion object {
        const val TAG = "ProcessCpuTracker"
    }

    // /proc/self/stat
    private val PROCESS_STATS_STATUS = 2 - 2
    private val PROCESS_STATS_MINOR_FAULTS = 9 - 2
    private val PROCESS_STATS_MAJOR_FAULTS = 11 - 2
    private val PROCESS_STATS_UTIME = 13 - 2
    private val PROCESS_STATS_STIME = 14 - 2

    // /proc/stat
    private val SYSTEM_STATS_USER_TIME = 2
    private val SYSTEM_STATS_NICE_TIME = 3
    private val SYSTEM_STATS_SYS_TIME = 4
    private val SYSTEM_STATS_IDLE_TIME = 5
    private val SYSTEM_STATS_IOWAIT_TIME = 6
    private val SYSTEM_STATS_IRQ_TIME = 7
    private val SYSTEM_STATS_SOFT_IRQ_TIME = 8

    // /proc/loadavg
    private val LOAD_AVERAGE_1_MIN = 0
    private val LOAD_AVERAGE_5_MIN = 1
    private val LOAD_AVERAGE_15_MIN = 2

    var jiffyMillis = 0L
    var currentProcId = 0
    private var currentProcStat: Stats
    var currentSampleTime = 0L
    var lastSampleTime = 0L
    var currentSampleRealTime = 0L
    var lastSampleRealTime = 0L
    var currentSampleWallTime = 0L
    var lastSampleWallTime = 0L
    var baseUserTime = 0L
    var baseSystemTime = 0L
    var baseIoWaitTime = 0L
    var baseIrqTime = 0L
    var baseSoftIrqTime = 0L
    var baseIdleTime = 0L
    var relUserTime = 0
    var relSystemTime = 0
    var relIoWaitTime = 0
    var relIrqTime = 0
    var relSoftIrqTime = 0
    var relIdleTime = 0
    var relStatsAreGood = false
    var DEBUG = true
    val buffer = ByteArray(4096)

    init {
        val jiffyHz = Sysconf.getScClkTck()
        jiffyMillis = 1000 / jiffyHz
        currentProcId = processId
        currentProcStat = Stats(currentProcId, false)
    }

    fun update() {
        val nowUptime = SystemClock.uptimeMillis()
        val nowRealtime = SystemClock.elapsedRealtime()
        val nowWalltime = System.currentTimeMillis()
        val sysCpu = readProcFile("/proc/stat")
        if (sysCpu != null) {
            val usertime = (sysCpu[SYSTEM_STATS_USER_TIME].toLong().plus(sysCpu[SYSTEM_STATS_NICE_TIME].toLong())
                ) * jiffyMillis
            val systemtime = sysCpu[SYSTEM_STATS_SYS_TIME].toLong() * jiffyMillis
            val idletime = sysCpu[SYSTEM_STATS_IDLE_TIME].toLong() * jiffyMillis
            val iowaittime = sysCpu[SYSTEM_STATS_IOWAIT_TIME].toLong() * jiffyMillis
            val irqtime = sysCpu[SYSTEM_STATS_IRQ_TIME].toLong() * jiffyMillis
            val softirqtime = sysCpu[SYSTEM_STATS_SOFT_IRQ_TIME].toLong() * jiffyMillis

            relUserTime = (usertime - baseUserTime).toInt()
            relSystemTime = (systemtime - baseSystemTime).toInt()
            relIoWaitTime = (iowaittime - baseIoWaitTime).toInt()
            relIrqTime = (irqtime - baseIrqTime).toInt()
            relSoftIrqTime = (softirqtime - baseSoftIrqTime).toInt()
            relIdleTime = (idletime - baseIdleTime).toInt()
            relStatsAreGood = true
            if (DEBUG) {
                Log.i(
                    TAG, "Total U:${usertime} S:${systemtime} I:${idletime}" +
                        " W:${iowaittime} Q:${irqtime} O:${softirqtime}"
                )
                Log.i(
                    TAG, "Rel U:${relUserTime} S:${relSystemTime}" +
                        " I:${relIdleTime} Q:${relIrqTime}"
                )
            }
            baseUserTime = usertime
            baseSystemTime = systemtime
            baseIoWaitTime = iowaittime
            baseIrqTime = irqtime
            baseSoftIrqTime = softirqtime
            baseIdleTime = idletime
        }
        lastSampleTime = currentSampleTime
        currentSampleTime = nowUptime
        lastSampleRealTime = currentSampleRealTime
        currentSampleRealTime = nowRealtime
        lastSampleWallTime = currentSampleWallTime
        currentSampleWallTime = nowWalltime
        getName(currentProcStat, currentProcStat.cmdlineFile)
        collectProcsStats("/proc/self/stat", currentProcStat)
        if (currentProcStat.workingThreads != null) {
            val threadsProcFiles = File(currentProcStat.threadsDir).listFiles()
            threadsProcFiles.forEach {
                val threadId = it.name.toInt()
                var threadStat = findThreadStat(threadId, currentProcStat.workingThreads)
                if (threadStat == null) {
                    threadStat = Stats(threadId, true)
                    getName(threadStat, threadStat.cmdlineFile)
                    currentProcStat.workingThreads?.add(threadStat)
                }
                collectProcsStats(threadStat.statFile, threadStat)
            }
            currentProcStat.workingThreads?.sortWith(Comparator { stat1, stat2 ->
                val t1 = stat1.rel_utime + stat1.rel_stime
                val t2 = stat2.rel_utime + stat2.rel_stime
                when (true) {
                    t1 > t2 -> -1
                    t1 < t2 -> 1
                    else -> 0
                }
            })
        }
    }

    private fun findThreadStat(threadId: Int, stats: ArrayList<Stats>?): Stats? {
        stats?.forEach {
            if (it.pid == threadId) {
                return it
            }
        }
        return null
    }

    private fun collectProcsStats(procFile: String, st: Stats) {
        val procStats = readProcFile(procFile)
        if (procStats == null || procStats.isEmpty()) {
            return
        }
        val status = procStats[PROCESS_STATS_STATUS]
        val minfaults = procStats[PROCESS_STATS_MINOR_FAULTS].toLong()
        val majfaults = procStats[PROCESS_STATS_MAJOR_FAULTS].toLong()
        val utime = procStats[PROCESS_STATS_UTIME].toLong()
        val stime = procStats[PROCESS_STATS_STIME].toLong()

        if (DEBUG) {
            Log.i(
                TAG, "Stats changed ${st.name} status:${status} pid:${st.pid}" +
                    " utime:${utime} - ${st.base_utime}" +
                    " stime:${stime} - ${st.base_stime}" +
                    " minfaults:${minfaults} - ${st.base_minfaults}" +
                    " majfaults:${majfaults} - ${st.base_majfaults}"
            )
        }
        val uptime = SystemClock.uptimeMillis()
        st.rel_uptime = uptime - st.base_uptime
        st.base_uptime = uptime
        st.rel_utime = utime - st.base_utime
        st.base_utime = utime
        st.rel_stime = stime - st.base_stime
        st.base_stime = stime
        st.rel_minfaults = minfaults - st.base_minfaults
        st.rel_majfaults = majfaults - st.base_majfaults
        st.base_minfaults = minfaults
        st.base_majfaults = majfaults
        st.status = status
    }

    fun getName(stats: Stats, cmdLineFile: String) {
        var backName = stats.name
        if (TextUtils.isEmpty(backName)
            || backName == "app_process"
            || backName == "<pre-initialized>") {
            val cmdName = readFile(cmdLineFile, 'c')
            val lastIndex = cmdName.lastIndexOf("/")
            if (lastIndex > 0) {
                backName = cmdName.substring(lastIndex + 1)
            }
            if (TextUtils.isEmpty(backName)) {
                backName = stats.baseName
            }
        }
        if (!TextUtils.isEmpty(backName)) {
            stats.name = backName
        }
    }

    fun readFile(file: String, endChar: Char): String {
        var fileInputStream: FileInputStream? = null
        try {
            fileInputStream = FileInputStream(file)
            val len = fileInputStream.read(buffer)
            fileInputStream.close()
            if (len > 0) {
                var i = 0
                for (i in 0..len) {
                    if (buffer[i] == endChar.toByte() || buffer[i] == 10.toByte()) {
                        break
                    }
                }
                return String(buffer, 0, i)
            }
        } catch (e: Exception) {

        } finally {
            SystemInfo.closeQuietly(fileInputStream)
        }
        return ""
    }

    private fun readProcFile(path: String): Array<String>? {
        var procFile: RandomAccessFile? = null
        try {
            procFile = RandomAccessFile(path, "r")
            var procFileContents = procFile.readLine()
            val rightIndex = procFileContents.indexOf(")")
            if (rightIndex > 0) {
                procFileContents = procFileContents.substring(rightIndex + 2)
            }
            return procFileContents.split(" ").toTypedArray()
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        } finally {
            SystemInfo.closeQuietly(procFile)
        }
    }

    class Stats(_pid: Int, isThread: Boolean) {
        var pid = 0
        var workingThreads: ArrayList<Stats>? = null
        var statFile = ""
        var cmdlineFile = ""
        var threadsDir = ""
        var baseName = ""
        var name = ""

        var base_uptime = 0L
        var rel_uptime = 0L
        var base_utime = 0L
        var base_stime = 0L
        var rel_utime = 0L
        var rel_stime = 0L
        var base_minfaults = 0L
        var base_majfaults = 0L
        var rel_minfaults = 0L
        var rel_majfaults = 0L
        var status = ""

        init {
            pid = _pid
            if (isThread) {
                val procDir = File("/proc/self/task", pid.toString())
                statFile = procDir.absolutePath + "/stat"
                cmdlineFile = File(procDir, "comm").toString()
            } else {
                val procDir = File("/proc", pid.toString())
                statFile = File(procDir, "stat").toString()
                cmdlineFile = File(procDir, "cmdline").toString()
                threadsDir = File(procDir, "task").toString()
                workingThreads = ArrayList()
            }
        }
    }
}
