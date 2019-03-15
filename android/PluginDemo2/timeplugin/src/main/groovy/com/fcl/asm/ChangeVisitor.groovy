package com.fcl.asm

import jdk.internal.org.objectweb.asm.ClassVisitor
import jdk.internal.org.objectweb.asm.MethodVisitor
import jdk.internal.org.objectweb.asm.Opcodes
import com.fcl.asm.RedefineAdvice

public class ChangeVisitor extends ClassVisitor {

    //记录文件名
    private String owner=""
    private ActivityAnnotationVisitor fileAnnotationVisitor = null

    ChangeVisitor(ClassVisitor classVisitor) {
        super(Opcodes.ASM5, classVisitor)
    }

    @Override
    void visit(int version, int access, String name, String signature, String superName,
        String[] interfaces) {
        super.visit(version, access, name, signature, superName, interfaces)
        this.owner = name
    }



    @Override
    MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        MethodVisitor mv = this.cv.visitMethod(access, name, desc, signature, exceptions)
        mv = RedefineAdvice(mv, access, owner, name, desc)
        return mv
    }
}
