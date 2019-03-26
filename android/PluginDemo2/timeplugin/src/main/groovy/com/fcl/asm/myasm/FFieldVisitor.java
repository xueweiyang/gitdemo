package com.fcl.asm.myasm;

/**
 * Created by galio.fang on 19-3-26
 */
public class FFieldVisitor {
    int api;
    FFieldVisitor fv;
    FFieldVisitor(int api) {
        this(api,null);
    }

    FFieldVisitor(int api,FFieldVisitor fieldVisitor){
        this.api=api;
        this.fv=fieldVisitor;
    }

    void visitAttribute(FAttribute attribute) {
        if (fv!= null){
            fv.visitAttribute(attribute);
        }
    }

    void visitEnd() {
        if (fv!=null){
            fv.visitEnd();
        }
    }
}
