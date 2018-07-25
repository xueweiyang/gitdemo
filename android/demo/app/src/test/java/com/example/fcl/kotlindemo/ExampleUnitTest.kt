package com.example.fcl.kotlindemo

import com.example.fcl.dadademo.util.DateUtil
import org.junit.Test

import org.junit.Assert.*
import java.text.ParseException
import java.util.Date
import kotlin.math.exp

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(5, 2 + 2)
    }

    val time = "2018-07-25 17:00:05"

    val timeStamp = 1532509205000L

    lateinit var date:Date

    init {
        date= Date()
    }

    @Test
    fun stampToDateTest() {
        assertEquals(time, DateUtil.stampToDate(timeStamp))
    }

}
