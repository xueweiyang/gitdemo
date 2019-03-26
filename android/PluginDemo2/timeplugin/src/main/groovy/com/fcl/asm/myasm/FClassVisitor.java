package com.fcl.asm.myasm;

/**
 * Created by galio.fang on 19-3-19
 */
public abstract class FClassVisitor {

    FClassVisitor cv;
    int api;

    FClassVisitor(int api) {
        this(api,null);
    }

    FClassVisitor(int api,FClassVisitor classVisitor) {
        this.api=api;
        this.cv=classVisitor;
    }

    public void visit(
        int version,
        int access,
        String name,
        String signature,
        String superName,
        String[] interfaces
    ) {
        if (cv != null) {
            cv.visit(version, access, name, signature, superName, interfaces);
        }
    }

    FMethodVisitor visitMethod(
        int access,
        String name,
        String desc,
        String signature,
        String[] exceptions
    ) {
        if (cv!=null) {
            return cv.visitMethod(access,name,desc,signature,exceptions);
        }
        return null;
    }

    void visitSource(String source,String debug) {
        if (cv!=null){
            cv.visitSource(source, debug);
        }
    }

    FFieldVisitor visitField(
        int access,
        String name,
        String desc,
        String signature,
        Object value
    ) {
        if (cv!=null){
            return cv.visitField(access,name,desc,signature,value);
        }
        return null;
    }
}
