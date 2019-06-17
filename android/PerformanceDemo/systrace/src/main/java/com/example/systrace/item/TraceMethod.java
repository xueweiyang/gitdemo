package com.example.systrace.item;

import com.example.systrace.retrace.MappingCollector;
import com.example.systrace.retrace.MethodInfo;
import jdk.internal.org.objectweb.asm.Opcodes;

public class TraceMethod {

    public int id;
    public int accessFlag;
    public String className;
    public String methodName;
    public String desc;

    public static TraceMethod create(int id, int accessFlag, String className, String methodName, String desc) {
        TraceMethod traceMethod = new TraceMethod();
        traceMethod.id = id;
        traceMethod.accessFlag = accessFlag;
        traceMethod.className = className.replace("/", ".");
        traceMethod.methodName = methodName;
        traceMethod.desc = desc.replace("/", ".");
        return traceMethod;
    }

    public void revert(MappingCollector processor){
        if (null==processor){
            return;
        }
        MethodInfo methodInfo = processor.or
    }

    /**
     * original -> proguard
     * @param processor
     */
    public void proguard(MappingCollector processor) {
       if (null == processor){
           return;
       }
        MethodInfo methodInfo = processor.obfuscatedMethodInfo(className,methodName,desc);
       this.methodName=methodInfo.originalName;
       this.desc=methodInfo.desc;
       this.className=processor.proguardClassName(className,className);
    }

    String getMethodName() {
        if (desc == null || isNativeMethod()) {
            return className + "." + methodName;
        } else {
            return className + "." + methodName + "." + desc;
        }
    }

    boolean isNativeMethod() {
        return (accessFlag & Opcodes.ACC_NATIVE) != 0;
    }
}
