package com.fcl.asm

import jdk.internal.org.objectweb.asm.AnnotationVisitor
import jdk.internal.org.objectweb.asm.MethodVisitor
import jdk.internal.org.objectweb.asm.Opcodes
import jdk.internal.org.objectweb.asm.commons.AdviceAdapter
import org.objectweb.asm.Type

public class RedefineAdvice extends AdviceAdapter {
    String owner = ""
    ActivityAnnotationVisitor activityAnnotationVisitor = null
    boolean inject = false

    protected RedefineAdvice(MethodVisitor mv, int access, String className, String name, String desc) {
        super(Opcodes.ASM5, mv, access, name, desc)
        owner = className
    }

    @Override
    AnnotationVisitor visitAnnotation(String desc, boolean visible) {
        AnnotationVisitor annotationVisitor = super.visitAnnotation(desc, visible)
        inject = Type.getDescriptor(Cost.class) == desc
        return annotationVisitor
    }

    @Override
    protected void onMethodEnter() {
        super.onMethodEnter()
        mv.visitVarInsn(ALOAD, 0)
        mv.visitMethodInsn(INVOKESTATIC, "com/test/aop/tools/ActivityTimeManager",
            activityAnnotationVisitor.value + "Start",
            "(Landroid/app/Activity;)V")
    }

    @Override
    protected void onMethodExit(int i) {
        super.onMethodExit(i)
        mv.visitVarInsn(ALOAD, 0)
        mv.visitMethodInsn(INVOKESTATIC, "com/test/aop/tools/ActivityTimeManager",
            activityAnnotationVisitor.value + "End",
            "(Landroid/app/Activity;)V")
    }
}
