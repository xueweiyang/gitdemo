package com.fcl.asm.myasm;

import jdk.internal.org.objectweb.asm.MethodVisitor;

/**
 * Created by galio.fang on 19-3-20
 */
public abstract class FMethodVisitor {

    int api;
    FMethodVisitor mv;

    FMethodVisitor(int api) {
        this(api,null);
    }

    FMethodVisitor(int api,FMethodVisitor methodVisitor) {
        this.api=api;
        this.mv=methodVisitor;
    }

}
