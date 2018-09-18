package com.example.fcl.apodemo2

import android.util.Log
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before

/**
 * Created by galio.fang on 18-9-17
 */
@Aspect
class ActivityAspect {
    val TAG="ActivityAspect"
    @Before("execution(* android.app.Activity.onCreate(..))")
    fun onmethod(joinPoint: JoinPoint) {
        Log.e(TAG, "aspect before:"+joinPoint.signature)
    }

    @Around("execution(* android.app.Activity.onCreate(..))")
fun onMethod(joinPoint: ProceedingJoinPoint) {
        Log.e(TAG, "around oncreate")
    }

}
