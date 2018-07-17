package com.example.fcl.dadademo.aop

import android.util.Log
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before

@Aspect
class ActivityAOP {

    private val TAG = "ActivityAOP"

    @Before("execution(* android.app.Activity.on**(..))")
    fun onActivityMethodBefore(joinPoint: JoinPoint) {
        val key = joinPoint.signature.toString()
        Log.e(TAG, "onActivityMethodBefore: " + key)
    }
}
