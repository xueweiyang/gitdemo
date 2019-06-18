package com.example.systrace.retrace;



import org.objectweb.asm.Type;

import java.util.*;

public class MappingCollector implements MappingProcessor {
    private final static String TAG = "MappingCollector";
    private final static int DEFAULT_CAPACITY = 2000;
    public HashMap<String, String> mObfuscatedRawClassMap = new HashMap<>(DEFAULT_CAPACITY); //混淆到正常名字
    public HashMap<String, String> mRawObfuscatedClassMap = new HashMap<>(DEFAULT_CAPACITY); //正常名字到混淆
    private final Map<String, Map<String, Set<MethodInfo>>> mObfuscatedClassMethodMap = new HashMap<>();
    private final Map<String, Map<String, Set<MethodInfo>>> mOriginalClassMethodMap = new HashMap<>();


    @Override
    public boolean processClassMapping(String className, String newClassName) {
        mObfuscatedRawClassMap.put(newClassName, className);
        mRawObfuscatedClassMap.put(className, newClassName);
        return true;
    }

    public MethodInfo originalMethodInfo(String className,String methodName,String desc) {
        DescInfo descInfo = parseMethodDesc(desc, false);

        Map<String,Set<MethodInfo>> methodMap = mObfuscatedClassMethodMap.get(className);
        if (methodMap != null){
            Set<MethodInfo> methodSet = methodMap.get(methodName);
            if (methodSet != null) {
                Iterator<MethodInfo> methodInfoIterator = methodSet.iterator();
                while (methodInfoIterator.hasNext()){
                    MethodInfo methodInfo = methodInfoIterator.next();
                    if (methodInfo.matches(descInfo.returnType, descInfo.arguments)) {
                        MethodInfo newMethodInfo = new MethodInfo(methodInfo);
                        newMethodInfo.desc=descInfo.desc;
                        return newMethodInfo;
                    }
                }
            }
        }

        MethodInfo defaultMethodInfo = MethodInfo.defaultObject();
        defaultMethodInfo.desc = descInfo.desc;
        defaultMethodInfo.originalName = methodName;
        return defaultMethodInfo;
    }

    /**
     * get obfuscated method info
     *
     * @param className
     * @param name
     * @param desc
     * @return
     */
    public MethodInfo obfuscatedMethodInfo(String className, String methodName, String desc) {
        DescInfo descInfo = parseMethodDesc(desc, true);
        Map<String, Set<MethodInfo>> methodMap = mOriginalClassMethodMap.get(className);
        if (methodMap != null) {
            Set<MethodInfo> methodSet = methodMap.get(methodName);
            if (null != methodSet) {
                Iterator<MethodInfo> methodInfoIterator = methodSet.iterator();
                while (methodInfoIterator.hasNext()) {
                    MethodInfo methodInfo = methodInfoIterator.next();
                    MethodInfo newMethodInfo = new MethodInfo(methodInfo);
                    obfuscatedMethodInfo(newMethodInfo);
                    if (newMethodInfo.matches(descInfo.returnType, descInfo.arguments)) {
                        newMethodInfo.desc = descInfo.desc;
                        return newMethodInfo;
                    }
                }
            }
        }
        MethodInfo defaultMethodInfo = MethodInfo.defaultObject();
        defaultMethodInfo.desc = descInfo.desc;
        defaultMethodInfo.originalName = methodName;
        return defaultMethodInfo;
    }

    void obfuscatedMethodInfo(MethodInfo methodInfo) {
        String methodArguments = methodInfo.originalArguments;
        String[] args = methodArguments.split(",");
        StringBuffer stringBuffer = new StringBuffer();
        for (String str : args) {
            String key = str.replace("[", "").replace("]", "");
            if (mRawObfuscatedClassMap.containsKey(key)) {
                stringBuffer.append(str.replace(key, mRawObfuscatedClassMap.get(key)));
            } else {
                stringBuffer.append(str);
            }
            stringBuffer.append(',');
        }
        if (stringBuffer.length() > 0) {
            stringBuffer.deleteCharAt(stringBuffer.length() - 1);
        }
        String methodReturnType = methodInfo.originalType;
        String key = methodReturnType.replace("[", "").replace("]", "");
        if (mRawObfuscatedClassMap.containsKey(key)) {
            methodReturnType = methodReturnType.replace(key, mRawObfuscatedClassMap.get(key));
        }
        methodInfo.originalArguments = stringBuffer.toString();
        methodInfo.originalType = methodReturnType;
    }

    DescInfo parseMethodDesc(String desc, boolean isRawToObfuscated) {
        DescInfo descInfo = new DescInfo();
        Type[] argsObj = Type.getArgumentTypes(desc);
        StringBuffer argumentsBuffer = new StringBuffer();
        StringBuffer descBuffer = new StringBuffer();
        descBuffer.append('(');
        for (Type type : argsObj) {
            String key = type.getClassName().replace("[", "").replace("]", "");
            if (isRawToObfuscated) {
                if (mRawObfuscatedClassMap.containsKey(key)) {
                    argumentsBuffer.append(type.getClassName().replace(key, mRawObfuscatedClassMap.get(key)));
                    descBuffer.append(type.toString().replace(key, mRawObfuscatedClassMap.get(key)));
                } else {
                    argumentsBuffer.append(type.getClassName());
                    descBuffer.append(type.toString());
                }
            } else {
                if (mObfuscatedRawClassMap.containsKey(key)) {
                    argumentsBuffer.append(type.getClassName().replace(key, mObfuscatedRawClassMap.get(key)));
                    descBuffer.append(type.toString().replace(key, mObfuscatedRawClassMap.get(key)));
                } else {
                    argumentsBuffer.append(type.getClassName());
                    descBuffer.append(type.toString());
                }
            }
            argumentsBuffer.append(',');
        }
        descBuffer.append(')');

        Type returnObj;
        try {
            returnObj = Type.getReturnType(desc);
        } catch (ArrayIndexOutOfBoundsException e) {
            returnObj = Type.getReturnType(desc + ";");
        }
        if (isRawToObfuscated) {
            String key = returnObj.getClassName().replace("[", "").replace("]", "");
            if (mRawObfuscatedClassMap.containsKey(key)) {
                descInfo.returnType = returnObj.getClassName().replace(key, mRawObfuscatedClassMap.get(key));
                descBuffer.append(returnObj.toString().replace(key, mRawObfuscatedClassMap.get(key)));
            } else {
                descInfo.returnType = returnObj.getClassName();
                descBuffer.append(returnObj.toString());
            }
        } else {
            String key = returnObj.getClassName().replace("[", "").replace("]", "");
            if (mObfuscatedRawClassMap.containsKey(key)) {
                descInfo.returnType = returnObj.getClassName().replace(key, mObfuscatedRawClassMap.get(key));
                descBuffer.append(returnObj.toString().replace(key, mObfuscatedRawClassMap.get(key)));
            } else {
                descInfo.returnType = returnObj.getClassName();
                descBuffer.append(returnObj.toString());
            }
        }

        //delete last
        if (argumentsBuffer.length() > 0) {
            argumentsBuffer.deleteCharAt(argumentsBuffer.length() - 1);
        }
        descInfo.arguments = argumentsBuffer.toString();
        descInfo.desc = descBuffer.toString();
        return descInfo;
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
        methodSet.add(new MethodInfo(className, methodReturnType, methodName, methodArguments));

        Map<String, Set<MethodInfo>> methodMap2 = mOriginalClassMethodMap.get(className);
        if (methodMap2 == null) {
            methodMap2 = new HashMap<>();
            mOriginalClassMethodMap.put(className, methodMap2);
        }
        Set<MethodInfo> methodSet2 = methodMap2.get(methodName);
        if (methodSet2 == null) {
            methodSet2 = new LinkedHashSet<>();
            methodMap2.put(methodName, methodSet2);
        }
        methodSet2.add(new MethodInfo(newClassName, methodReturnType, newMethodName, methodArguments));
    }

    public String originalClassName(String proguardClassName, String defaultClassName) {
        if (mObfuscatedRawClassMap.containsKey(proguardClassName)) {
            return mObfuscatedRawClassMap.get(proguardClassName);
        } else {
            return defaultClassName;
        }
    }

    public String proguardClassName(String originalClassName, String defaultClassName) {
        if (mRawObfuscatedClassMap.containsKey(originalClassName)) {
            return mRawObfuscatedClassMap.get(originalClassName);
        } else {
            return defaultClassName;
        }
    }

    class DescInfo {
        public String desc;
        public String arguments;
        public String returnType;
    }
}
