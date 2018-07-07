package com.example.datepicker.timer

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.example.datepicker.R
import com.example.datepicker.adapter.NumericWheelAdapter
import kotlinx.android.synthetic.main.layout_timer.view.dayWheel
import kotlinx.android.synthetic.main.layout_timer.view.monthWheel
import kotlinx.android.synthetic.main.layout_timer.view.yearWheel

class TimerView constructor(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {

    private val DEFAULT_START_YEAR = 1900
    private val DEFAULT_END_YEAR = 2100
    private val DEFAULT_START_MONTH = 1
    private val DEFAULT_END_MONTH = 12
    private val DEFAULT_START_DAY = 1
    private val DEFAULT_END_DAY = 31

    private var startYear = DEFAULT_START_YEAR
    private var endYear = DEFAULT_END_YEAR
    private var startMonth = DEFAULT_START_MONTH
    private var endMonth = DEFAULT_END_MONTH
    private var startDay = DEFAULT_START_DAY
    private var endDay = DEFAULT_END_DAY

    private var curYear: Int = 0
    private var curMonth: Int = 0
    private val bigMonths = arrayListOf<Int>(1, 3, 5, 7, 8, 10, 12)
    private val littleMonths = arrayListOf<Int>(4, 6, 9, 11)

    init {
        LayoutInflater.from(context).inflate(R.layout.layout_timer, this)
        initTimer()
    }

    private fun initTimer() {
        yearWheel.adapter = NumericWheelAdapter(startYear, endYear)
        monthWheel.adapter = NumericWheelAdapter(startMonth, endMonth)
        dayWheel.adapter = NumericWheelAdapter(startDay, endDay)
    }

    fun setScale(startYear: Int, endYear: Int, startMonth: Int, endMonth: Int, startDay: Int, endDay: Int) {
        this.startYear = startYear
        this.endYear = endYear
        this.startMonth = startMonth
        this.endMonth = endMonth
        this.startDay = startDay
        this.endDay = endDay
        initTimer()
    }

    fun setTime(year: Int, month: Int, day: Int) {
        curYear = year
        curMonth = month +1
        yearWheel.currentItem = year - startYear
        monthWheel.currentItem = month - startMonth
        dayWheel.currentItem = day - startDay

        changeMonth(year, month)
        changeDay(year, month, day)

        yearWheel.setOnItemSelectedListener {
            val yearNum = it + startYear
            curYear = yearNum
            changeMonth(curYear, monthWheel.currentItem)
            changeDay(curYear, monthWheel.currentItem, dayWheel.currentItem)
        }

        monthWheel.setOnItemSelectedListener {
            curMonth = it + startMonth
            changeDay(curYear, it, dayWheel.currentItem)
        }
    }

    fun getTime() : String {
        return String.format("%4d-%2d-%2d", curYear,curMonth+1,dayWheel.currentItem)
    }

    /**
     * 当年份和月份改变时，日期作出改变
     */
    private fun changeDay(year: Int, month: Int, day: Int) {
        var sDay = startDay
        var eDay = endDay
        var curDay = day
        if (startYear == endYear && startMonth == endMonth) {
            eDay = getEndDay(month, eDay, year)
            sDay = startDay
            curDay = day - startDay
        } else if (year == startYear && month + 1 == startMonth) {
            eDay = getEndDay(month, eDay, year)
            sDay = startDay
            curDay = day - startDay
        } else if (year == endYear && month + 1 == endMonth) {
            eDay = getEndDay(month, eDay, year)
            sDay = 1
            curDay = day
        } else {
            if (bigMonths.contains(month + 1)) {
                eDay = 31
            } else if (littleMonths.contains(month + 1)) {
                eDay = 30
            } else {
                eDay = if (isLeapYear(year)) 29 else 28
            }
            sDay = 1
            curDay = day
        }
        dayWheel.adapter = NumericWheelAdapter(sDay, eDay)
        dayWheel.currentItem = curDay
    }

    private fun getEndDay(month: Int, eDay: Int, year: Int): Int {
        var eDay1 = eDay
        if (bigMonths.contains(month + 1)) {
            eDay1 = Math.max(eDay1, 31)
        } else if (littleMonths.contains(month + 1)) {
            eDay1 = Math.max(eDay1, 30)
        } else {
            eDay1 = Math.max(eDay1, if (isLeapYear(year)) 29 else 28)
        }
        return eDay1
    }

    /**
     * 是否是闰年
     */
    fun isLeapYear(year: Int): Boolean {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)
    }

    /**
     * 当年份改变时，根据设置的月份范围作出改变
     */
    private fun changeMonth(year: Int, month: Int) {
        var sMonth = startMonth
        var eMonth = endMonth
        var curMonth = month
        if (startYear == endYear) {
            sMonth = startMonth
            eMonth = endMonth
            curMonth = month + 1 - startMonth
        } else if (year == startYear) {
            sMonth = startMonth
            eMonth = 12
            curMonth = month + 1 - startMonth
        } else if (year == endYear) { //假设设置当前时间为最大年月，则按该时间为准
            sMonth = 1
            eMonth = endMonth
        } else {
            sMonth = 1
            eMonth = 12
        }
        monthWheel.adapter = NumericWheelAdapter(sMonth, eMonth)
        monthWheel.currentItem = curMonth
    }
}