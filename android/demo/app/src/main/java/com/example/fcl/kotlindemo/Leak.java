package com.example.fcl.kotlindemo;

import android.content.Context;

public class Leak {

    public static Leak leak;

    private Context context;

    public Leak(Context context) {
        this.context = context;
    }

    public static Leak getLeak(Context context) {
        if (leak==null){
            leak=new Leak(context);
        }
        return leak;
    }

}
