package com.example.systrace.retrace;

public class MethodInfo {

    public String originalClassName;
    public String originalType;
    public String originalArguments;
    public String originalName;
    public String desc;

    public MethodInfo(String originalClassName,
                      String originalType,
                      String originalName,
                      String originalArguments) {
        this.originalType=originalType;
        this.originalArguments=originalArguments;
        this.originalClassName=originalClassName;
        this.originalName=originalName;
    }

    public MethodInfo(MethodInfo methodInfo) {
        this.originalType = methodInfo.originalType;
        this.originalArguments=methodInfo.originalArguments;
        this.originalClassName=methodInfo.originalClassName;
        this.originalName=methodInfo.originalName;
        this.desc=methodInfo.desc;
    }

}
