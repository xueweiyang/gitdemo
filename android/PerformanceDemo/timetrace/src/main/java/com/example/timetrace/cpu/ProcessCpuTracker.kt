package com.example.timetrace.cpu

import android.os.SystemClock
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.io.RandomAccessFile
import java.util.*
import kotlin.Comparator

class ProcessCpuTracker constructor(procId: Int) {

    // /proc/self/stat
    private val PROCESS_STATS_STATUS = 2 - 2
    private val PROCESS_STATS_MINOR_FAULTS = 9 - 2
    private val PROCESS_STATS_MAJOR_FAULTS = 11 - 2
    private val PROCESS_STATS_UTIME = 13 - 2
    private val PROCESS_STATS_STIME = 14 - 2

    // /proc/
    private val NR_VOLUNTARY_SWITCHES = "nr_voluntary_switches"
    private val NR_INVOLUNTARY_SWITCHES = "nr_involuntary_switches"
    private val SE_IOWAIT_COUNT = "se.statistics.iowait_count"
    private val SE_IOWAIT_SUM = "se.statistics.iowait_sum"

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

    lateinit var procStat: Stats
    var jiffyMillis = 0L
    var curUserTime = 0L
    var lastUserTime = 0L

    var lastSampleTime = 0L
    var curSampleTime = 0L

    var lastSampleRealTime = 0L
    var curSampleRealTime = 0L

    var lastSampleWallTime = 0L
    var lastsystemtime = 0L
    var lastidletime = 0L
    var lastirqtime = 0L
    var lastsoftirqtime = 0L

    var curSampleWallTime = 0L
    var cursystemtime = 0L
    var curidletime = 0L
    var curirqtime = 0L
    var cursoftirqtime = 0L

    var load1=0f
    var load5=0f
    var load15=0f


    init {
//        val jiffyHz = Os.sysconf(OsConstants._SC_CLK_TCK)
        val jiffyHz = 16L
        jiffyMillis = 1000 / jiffyHz
        procStat = Stats(procId, false)
    }

    fun update() {
        val nowUptime = SystemClock.uptimeMillis()
        val nowRealtime = SystemClock.elapsedRealtime()
        val nowWallTime = System.currentTimeMillis()
        val sysCpu = readProcFile("/proc/stat")

        if (!sysCpu.isNullOrEmpty()) {
            val usertime =
                (sysCpu[SYSTEM_STATS_USER_TIME].toLong() + sysCpu[SYSTEM_STATS_NICE_TIME].toLong()) * jiffyMillis
            val systemtime = sysCpu[SYSTEM_STATS_SYS_TIME].toLong() * jiffyMillis
            val idletime = sysCpu[SYSTEM_STATS_IDLE_TIME].toLong() * jiffyMillis
            val irqtime = sysCpu[SYSTEM_STATS_IRQ_TIME].toLong() * jiffyMillis
            val softirqtime = sysCpu[SYSTEM_STATS_SOFT_IRQ_TIME].toLong() * jiffyMillis

            curUserTime = usertime - lastUserTime
            cursystemtime = systemtime - lastsystemtime
            curidletime = idletime - lastidletime
            curirqtime = irqtime - lastirqtime
            cursoftirqtime = softirqtime - lastsoftirqtime

            lastUserTime = usertime
            lastsystemtime = systemtime
            lastidletime = idletime
            lastirqtime = irqtime
            lastsoftirqtime = softirqtime
        }
        lastSampleTime = curSampleTime
        curSampleTime = nowUptime
        lastSampleRealTime = curSampleRealTime
        curSampleRealTime = nowRealtime
        lastSampleWallTime = curSampleWallTime
        curSampleWallTime = nowWallTime

        getName(procStat, procStat.cmdlineFile)
        collectProcStats("/proc/self/stat", procStat)

        if (procStat.workingThreads.isNotEmpty()) {
            File(procStat.threadsDir).listFiles().forEach { file ->
                val threadId = file.name.toInt()
                var threadStat = findThreadStat(threadId, procStat.workingThreads)
                if (threadStat == null){
                    threadStat = Stats(threadId, true)
                    getName(threadStat, threadStat.cmdlineFile)
                    procStat.workingThreads.add(threadStat)
                }
                collectProcStats(threadStat.statFile, threadStat)
            }
            Collections.sort(procStat.workingThreads, object :Comparator<Stats>{
                override fun compare(stat1: Stats, stat2: Stats): Int {
                    val t1 = stat1.curUTime+stat1.curSTime
                    val t2 = stat2.curUTime+stat2.curSTime
                    if (t1 != t2){
                        return if (t1>t2) -1 else 1
                    }
                    return 0
                }
            })
        }

        val loadAverages = readProcFile("/proc/loadavg")
        if (loadAverages!= null) {
            val load1 = loadAverages[LOAD_AVERAGE_1_MIN].toFloat()
            val load5 = loadAverages[LOAD_AVERAGE_5_MIN].toFloat()
            val load15 = loadAverages[LOAD_AVERAGE_15_MIN].toFloat()
            if (this.load1 != load1 || this.load5!=load5||this.load15!=load15){
                this.load1=load1
                this.load5=load5
                this.load15=load15
            }
        }

    }

    fun findThreadStat(id: Int, stats: List<Stats>): Stats? {
        stats.forEach {
            if (it.pid == id) {
                return it
            }
        }
        return null
    }

    fun collectProcStats(procFile: String, stats: Stats) {
        val procStats = readProcFile(procFile) ?: return
        val status = procStats[PROCESS_STATS_STATUS]
        val minfaults = procStats[PROCESS_STATS_MINOR_FAULTS].toLong()
        val majfaults = procStats[PROCESS_STATS_MAJOR_FAULTS].toLong()
        val utime = procStats[PROCESS_STATS_UTIME].toLong() * jiffyMillis
        val stime = procStats[PROCESS_STATS_STIME].toLong() * jiffyMillis

        val uptime = SystemClock.uptimeMillis()

        stats.apply {
            curUpTime = uptime - lastUpTime
            lastUpTime = uptime
            curUTime = utime - lastUTime
            lastUTime = utime
            curSTime = stime - lastSTime
            lastSTime = stime
            curMinfaults = minfaults - lastMinfaults
            lastMinfaults = minfaults
            curMajfaults = majfaults - lastMajfaults
            lastMajfaults = majfaults
            this.status = status
        }
    }

    fun getName(stats: Stats, cmdlineFile: String) {
        var name = stats.name
        if (name.isEmpty() || "app_process" == name
            || "<pre-initialized>" == name
        ) {
            val cmdName = readFile(cmdlineFile, "\\0")
            if (!cmdName.isNullOrEmpty()) {
                name = cmdName
                val i = name.lastIndexOf("/")
                if (i in 0..name.length) {
                    name = name.substring(i + 1)
                }
                if (name.isNullOrEmpty()) {
                    name = stats.baseName
                }
            }
        }
        if (stats.name.isNullOrEmpty() || stats.name != name) {
            stats.name = name
        }
    }

    val buffer = ByteArray(4096)
    fun readFile(file: String, endChar: String): String {
        FileInputStream(file).use {
            val len = it.read(buffer)
            if (len > 0) {
                buffer.forEachIndexed { index, it ->
                    if (it == endChar.toByte() || it == 10.toByte()) {
                        return String(buffer, 0, index)
                    }
                }
            }
        }
        return ""
    }

    private fun readProcFile(name: String): List<String>? {
        var procFile: RandomAccessFile? = null
        try {
            procFile = RandomAccessFile(name, "r")
            var contents = procFile.readLine()
            val rightIndex = contents.indexOf(")")
            if (rightIndex > 0) {
                contents = contents.substring(rightIndex + 2)
            }
            return contents.split(" ")
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        } finally {
            procFile?.close()
        }
    }

    class Stats constructor(procId: Int, isThread: Boolean) {

        var statFile = ""
        var cmdlineFile = ""
        var threadsDir = ""
        var workingThreads = arrayListOf<Stats>()
        var name = ""
        var baseName = ""

        var curUpTime = 0L
        var curUTime = 0L
        var curSTime = 0L
        var curMinfaults = 0L
        var curMajfaults = 0L

        var lastUpTime = 0L
        var lastUTime = 0L
        var lastSTime = 0L
        var lastMinfaults = 0L
        var lastMajfaults = 0L

        var status = ""
        var pid = 0

        init {
            if (isThread) {
                val procDir = File("/proc/self/task", procId.toString()).absolutePath
                statFile = "$procDir/stat"
                cmdlineFile = File(procDir, "comm").absolutePath
            } else {
                val procDir = File("/proc", procId.toString())
                statFile = File(procDir, "stat").absolutePath
                cmdlineFile = File(procDir, "cmdline").absolutePath
                threadsDir = File(procDir, "task").absolutePath
            }
        }

    }
}