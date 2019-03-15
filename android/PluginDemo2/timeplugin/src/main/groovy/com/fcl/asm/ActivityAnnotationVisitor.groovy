package com.fcl.asm

import jdk.internal.org.objectweb.asm.AnnotationVisitor

public class ActivityAnnotationVisitor extends AnnotationVisitor {

    String desc
    String name
    String value

    ActivityAnnotationVisitor(int api, AnnotationVisitor annotationVisitor,String desc) {
        super(api, annotationVisitor)
        this.desc = desc
    }

    @Override
    void visit(String paramName, Object paramValue) {
        this.name = paramName
        this.value = paramValue.toString()
    }
}
