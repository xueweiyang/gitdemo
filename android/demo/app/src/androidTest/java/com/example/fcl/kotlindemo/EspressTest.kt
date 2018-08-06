package com.example.fcl.kotlindemo

import android.support.test.espresso.Espresso.closeSoftKeyboard
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.typeText
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.example.fcl.kotlindemo.test.EspressActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by galio.fang on 18-8-6
 */

@RunWith(AndroidJUnit4::class)
@LargeTest
class EspressTest{

    @Rule
    var activityRule = ActivityTestRule(EspressActivity::class.java)

    @Test
    fun sayHello() {
//        onView(withId(R.id.edit)).perform(typeText("Peter"), closeSoftKeyboard())
    }

}