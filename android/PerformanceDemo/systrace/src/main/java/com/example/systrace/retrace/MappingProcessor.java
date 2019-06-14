package com.example.systrace.retrace;

public interface MappingProcessor {

    boolean processClassMapping(String className, String newClassName);

    void processMethodMapping(String className,
                              String methodReturnType,
                              String methodName,
                              String methodArguments,
                              String newClassName,
                              String newMethodName);

}
