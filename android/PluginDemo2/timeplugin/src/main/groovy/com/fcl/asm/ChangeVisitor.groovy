package com.fcl.asm

import jdk.internal.org.objectweb.asm.ClassVisitor
import jdk.internal.org.objectweb.asm.FieldVisitor
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
        println("method name:$name")
        if (name == "show") {
return null
        }
        return cv.visitMethod(access,name,desc,signature,exceptions)
    }

    @Override
    FieldVisitor visitField(int access, String name, String s1, String s2, Object o) {
        int acc = Opcodes.ACC_PRIVATE
        if (name == "testFlag") {

            return cv.visitField(acc, name+"_asm", s1, s2, o)
        } else {
            return cv.visitField(access, name, s1, s2, o)

        }
    }
}
