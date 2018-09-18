package com.example.fcl.aopdemo.aop

import android.util.Log
import android.view.View
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.After
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before

/**
 * Created by galio.fang on 18-9-14
 */
//@Aspect
class ClickAspect {
val TAG = "ClickAspect"
//    @Before("call(* android.view.View.setOnClickListener(..))&&withincode(* com.example.fcl.aopdemo.MainActivity" +
//        ".onCreate(..))")
//    @Throws(Throwable::class)
//    fun click(joinPoint: ProceedingJoinPoint) {
//        val objects= arrayOfNulls<Any>(1)
//        Log.e(TAG, "click1")
//        objects[0]=View.OnClickListener {
//            Log.e(TAG, "click")
//
//        }
//        joinPoint.proceed(objects)
//    }

//    @After("execution(* android.view.View.OnClickListener.onClick(android.view.View))")
//    fun onViewClick(joinPoint: JoinPoint) {
//        Log.e(TAG, "onviewclick")
//    }
}
