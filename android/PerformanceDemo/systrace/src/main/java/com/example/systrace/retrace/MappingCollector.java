package com.example.systrace.retrace;


import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class MappingCollector implements MappingProcessor {
    private final static String TAG = "MappingCollector";
    private final static int DEFAULT_CAPACITY = 2000;
    public HashMap<String, String> mObfuscatedRawClassMap = new HashMap<>(DEFAULT_CAPACITY);
    public HashMap<String, String> mRawObfuscatedClassMap = new HashMap<>(DEFAULT_CAPACITY);
    private final Map<String, Map<String, Set<MethodInfo>>> mObfuscatedClassMethodMap = new HashMap<>();
    private final Map<String, Map<String, Set<MethodInfo>>> mOriginalClassMethodMap = new HashMap<>();


    @Override
    public boolean processClassMapping(String className, String newClassName) {
        mObfuscatedRawClassMap.put(newClassName,className);
        mRawObfuscatedClassMap.put(className,newClassName);
        return true;
    }

    @Override
    public void processMethodMapping(String className, String methodReturnType, String methodName, String methodArguments, String newClassName, String newMethodName) {
        newClassName = mRawObfuscatedClassMap.get(className);
        Map<String, Set<MethodInfo>> methodMap = mObfuscatedClassMethodMap.get(newClassName);
        if (methodMap == null) {
            methodMap = new HashMap<>();
            mObfuscatedClassMethodMap.put(newClassName, methodMap);
        }
        Set<MethodInfo> methodSet = methodMap.get(newMethodName);
        if (methodSet == null) {
            methodSet = new LinkedHashSet<>();
            methodMap.put(newMethodName, methodSet);
        }
        methodSet.add(new MethodInfo(className,methodReturnType,methodName,methodArguments));

        Map<String,Set<MethodInfo>> methodMap2 = mOriginalClassMethodMap.get(className);
        if (methodMap2==null){
            methodMap2=new HashMap<>();
            mOriginalClassMethodMap.put(className,methodMap2);
        }
        Set<MethodInfo> methodSet2 = methodMap2.get(methodName);
        if (methodSet2==null){
            methodSet2=new LinkedHashSet<>();
            methodMap2.put(methodName, methodSet2);
        }
        methodSet2.add(new MethodInfo(newClassName,methodReturnType,newMethodName,methodArguments));
    }
}
