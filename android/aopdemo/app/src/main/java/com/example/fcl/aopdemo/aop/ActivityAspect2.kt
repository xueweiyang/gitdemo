package com.example.fcl.aopdemo.aop

import android.util.Log
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.aspectj.lang.annotation.Pointcut

@Aspect
class ActivityAspect2 {

    @Pointcut(POINT_METHOD)
    fun methodAnnotated() {
    }

    @Before("execution(* android.app.Activity.onCreate(..))")
    fun onresumeMethod(joinPoint: JoinPoint) {
        Log.e("helloAop", "aspect before::" + joinPoint.signature)
    }

    @Around("execution(* android.app.Activity.onCreate(..))")
    @Throws(Throwable::class)
    fun onMethod(joinPoint: ProceedingJoinPoint) {
        Log.e("helloAop", "aspect around::" + joinPoint.signature)
        joinPoint.proceed()
        Log.e("helloAop", "aspect2 around::" + joinPoint.signature)
    }

    companion object {

        const val POINT_METHOD = "execution(* com.example.fcl.aopdemo.MainActivity.onCreate(..))"
    }
}
