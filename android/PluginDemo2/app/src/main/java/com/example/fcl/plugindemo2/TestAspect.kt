package com.example.fcl.plugindemo2

import android.util.Log
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.aspectj.lang.annotation.Pointcut

/**
 * Created by galio.fang on 18-8-17
 */
@Aspect
public class TestAspect {

    @Pointcut("execution(* android.app.Activity.onCreate(..))")
    fun methodAnnotated() {
    }

    @Before("methodAnnotated()")
    fun aroundJoinPoint(joinPoint: JoinPoint) {
        Log.e("TestAspect", "insert code")
    }
}