package com.example.fcl.plugindemo2;

import java.io.File;

/**
 * Created by galio.fang on 18-8-23
 */
public class Cat<T> {

    int testFlag=1;
    static final int a = 3;
    @Deprecated
    static final String B = "哈哈哈";

    public String show() throws NullPointerException {
        File file = new File("");
        return "汪汪汪";
    }

    class CatA {

    }
}
