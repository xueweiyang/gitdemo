package com.example.fcl.dadademo.util

import com.example.fcl.dadademo.util.DateUtil.format.FORMAT
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DateUtil{

    object format {
        const val FORMAT = "yyyy-MM-dd HH:mm:ss"
    }

    /**
     * 时间转时间戳
     */
    fun dateToStamp(time:String) : Long {
        val dateFormat = SimpleDateFormat(FORMAT, Locale.CHINA)
        val date = dateFormat.parse(time)
        return date.time
    }

    /**
     * 时间戳转时间
     */
    open fun stampToDate(time:Long) : String {
        try {
            val dateFormat = SimpleDateFormat(FORMAT, Locale.CHINA)
            val date = Date(time)
            return dateFormat.format(date)
        } catch (e:ParseException) {
            return ""
        }

    }

}