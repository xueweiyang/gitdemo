package com.fcl.asm.myasm;

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
