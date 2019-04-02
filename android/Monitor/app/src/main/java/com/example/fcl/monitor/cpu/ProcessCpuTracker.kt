package com.example.fcl.monitor.cpu

import android.os.SystemClock
import java.io.File
import java.io.IOException
import java.io.RandomAccessFile

/**
 * Created by galio.fang on 19-4-2
 */
class ProcessCpuTracker(processId : Int) {

    var jiffyMillis=0L
    var currentProcId = 0
    private var currentProcStat : Stats

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
    }

    private fun readProcFile(path: String): Array<String>? {
        var procFile:RandomAccessFile?=null
        try {
            procFile = RandomAccessFile(path,"r")
var procFileContents = procFile.readLine()
            val rightIndex = procFileContents.indexOf(")")
            if (rightIndex >0){
                procFileContents = procFileContents.substring(rightIndex+2)
            }
            return procFileContents.split(" ").toTypedArray()
        } catch (e:IOException){
            e.printStackTrace()
            return null
        } finally {
            Sys
        }
    }

    class Stats(_pid:Int,isThread:Boolean) {
        var pid=0
        var workingThreads : ArrayList<Stats> ?=null
        var statFile=""
        var cmdlineFile=""
        var threadsDir=""
        var baseName=""
        var name=""

        init {
            pid = _pid
            if (isThread) {
                val procDir = File("/proc/self/task", pid.toString())
                statFile = procDir.absolutePath + "/stat"
                cmdlineFile = File(procDir,"comm").toString()
            } else {
                val procDir = File("/proc", pid.toString())
                statFile = File(procDir,"stat").toString()
                cmdlineFile = File(procDir,"cmdline").toString()
                threadsDir = File(procDir,"task").toString()
                workingThreads = ArrayList()
            }
        }

    }

}
