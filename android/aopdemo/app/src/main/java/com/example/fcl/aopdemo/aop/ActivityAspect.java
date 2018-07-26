package com.example.fcl.aopdemo.aop;

import android.util.Log;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class ActivityAspect {

    static final String POINT_METHOD = "execution(* com.example.fcl.aopd.MainActivity.onCreate(..))";

    @Pointcut(POINT_METHOD)
    public void methodAnnotated() {

    }

    @Before("execution(* android.app.Activity.onCreate(..))")
    public void onresumeMethod(JoinPoint joinPoint) {
        Log.e("helloAop", "aspect before::"+joinPoint.getSignature());
    }


    @Around("execution(* android.app.Activity.onCreate(..))")
    public void onMethod(ProceedingJoinPoint joinPoint) throws Throwable{
        Log.e("helloAop", "aspect around::"+joinPoint.getSignature());
        joinPoint.proceed();
        Log.e("helloAop", "aspect2 around::"+joinPoint.getSignature());
    }

}
