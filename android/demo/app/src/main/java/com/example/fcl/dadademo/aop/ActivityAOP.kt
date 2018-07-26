package com.example.fcl.dadademo.aop

import android.util.Log
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.aspectj.lang.annotation.Pointcut

const val POINT_METHOD = "execution(* android.app.Activity.onCreate(..))"

@Aspect
class ActivityAOP {

    private val TAG = "ActivityAOP"

    @Pointcut(POINT_METHOD)
    fun methodAnnotated() {
    }

    @Before("methodAnnotated()")
    fun onActivityMethodBefore(joinPoint: JoinPoint) {
        val key = joinPoint.signature.toString()
        Log.e(TAG, "onActivityMethodBefore: " + key)
    }
}
