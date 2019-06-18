package com.example.systrace;

import com.example.systrace.retrace.MappingCollector;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;

public class TraceBuildConfig {
    String TAG = "TraceBuildConfig";
    String packageName;
    String mappingPath;
    String baseMethodMap;
    String methodMapFile;
    String ignoreMethodMapFile;
    String blackListDir;
    HashSet<String> blackClassMap = new HashSet<>();
    HashSet<String> blackPackageMap = new HashSet<>();

    TraceBuildConfig(String packageName, String mappingPath, String baseMethodMap,
                     String methodMapFile, String ignoreMethodMapFile, String blackListDir) {
        this.packageName = packageName;
        this.mappingPath = mappingPath;
        this.baseMethodMap = baseMethodMap;
        this.methodMapFile = methodMapFile;
        this.ignoreMethodMapFile = ignoreMethodMapFile;
        this.blackListDir = blackListDir;
    }

    public String getPackageName() {
        return packageName;
    }

    public String getMappingPath() {
        return mappingPath;
    }

    public String getBaseMethodMap() {
        return baseMethodMap;
    }

    public String getMethodMapFile() {
        return methodMapFile;
    }

    public String getIgnoreMethodMapFile() {
        return ignoreMethodMapFile;
    }

    public String getBlackListDir() {
        return blackListDir;
    }

    public HashSet<String> getBlackClassMap() {
        return blackClassMap;
    }

    public HashSet<String> getBlackPackageMap() {
        return blackPackageMap;
    }

    void parseBlackFile(MappingCollector processor) {
        File blackConfigFile = new File(blackListDir);
        if (!blackConfigFile.exists()) {
            Log.i(TAG, "black config file not exist " + blackConfigFile.getAbsoluteFile());
        }
        String blackStr = TraceBuildConstants.DEFAULT_BLACK_TRACE + Util.readFileAsString(blackListDir);
        String[] blackArray = blackStr.split("\n");
        for (String black : blackArray) {
            black = black.trim().replace("/", ".");
            if (black.length() == 0) {
                continue;
            }
            if (black.startsWith("#")) {
//                Log.i(TAG, "[parseBlackFile] comment:%s", black);
                continue;
            }
            if (black.startsWith("[")){
                continue;
            }
            if (black.startsWith("-keepclass ")) {
                black = black.replace("-keepclass ","");
                blackClassMap.add(processor.proguardClassName(black, black));
            } else if (black.startsWith("-keeppackage ")) {
                black = black.replace("-keeppackage ", "");
                blackPackageMap.add(black);
            }
        }
    }

    public boolean isMethodBeatClass(String className, HashMap<String,String> collectClassExtendMap){
        className=className.replace(".","/");
        boolean isApplication = className.equals(TraceBuildConstants.MATRIX_TRACE_METHOD_BEAT_CLASS);
        if (isApplication){
            return true;
        } else if (collectClassExtendMap.containsKey(className)){
            return collectClassExtendMap.get(className).equals(TraceBuildConstants.MATRIX_TRACE_METHOD_BEAT_CLASS);
        } else {
            return false;
        }
    }

    public boolean isNeedTraceClass(String fileName){
        boolean isNeed=true;
        if (fileName.endsWith(".class")){
            for (String unTraceCls:TraceBuildConstants.UN_TRACE_CLASS){
                if (fileName.contains(unTraceCls)){
                    isNeed=false;
                    break;
                }
            }
        } else {
            isNeed=false;
        }
//        if (isNeed) {
//
//            Log.e(TAG, String.format("%s need trace %b", fileName, isNeed));
//        }
        return isNeed;
    }

    public boolean isWindowFocusChangeMethod(String name,String desc){
        return null!=name&&null!=desc&&name.equals(TraceBuildConstants.MATRIX_TRACE_ON_WINDOW_FOCUS_METHOD)
                &&desc.equals(TraceBuildConstants.MATRIX_TRACE_ON_WINDOW_FOCUS_METHOD_ARGS);
    }

    public boolean isActivityOrSubClass(String className,HashMap<String,String> collectedClassExtendMap){
        className = className.replace(".","/");
        boolean isActivity = className.equals(TraceBuildConstants.MATRIX_TRACE_ACTIVITY_CLASS)
                || className.equals(TraceBuildConstants.MATRIX_TRACE_V7_ACTIVITY_CLASS);
        if (isActivity){
            return true;
        } else {
            if (!collectedClassExtendMap.containsKey(className)){
                return false;
            } else {
                return isActivityOrSubClass(collectedClassExtendMap.get(className), collectedClassExtendMap);
            }
        }
    }

    public boolean isNeedTrace(String className,MappingCollector mappingCollector) {
        boolean isNeed = true;
        if (blackClassMap.contains(className)){
            isNeed = false;
        } else {
            if (null!=mappingCollector){
                className=mappingCollector.originalClassName(className,className);
            }
            for (String packageName:blackPackageMap) {
                if (className.startsWith(packageName.replaceAll("/","."))) {
                    isNeed = false;
                    break;
                }
            }
        }
        return isNeed;
    }

    public static class Builder {

        String packageName;
        String mappingPath;
        String baseMethodMap;
        String methodMapFile;
        String ignoreMethodMapFile;
        String blackListDir;

        public Builder setPackageName(String packageName) {
            this.packageName = packageName;
            return this;
        }

        public Builder setMappingPath(String mappingPath) {
            this.mappingPath = mappingPath;
            return this;
        }

        public Builder setBaseMethodMap(String baseMethodMap) {
            this.baseMethodMap = baseMethodMap;
            return this;
        }

        public Builder setMethodMapFile(String methodMapFile) {
            this.methodMapFile = methodMapFile;
            return this;
        }

        public Builder setIgnoreMethodMapFile(String ignoreMethodMapFile) {
            this.ignoreMethodMapFile = ignoreMethodMapFile;
            return this;
        }

        public Builder setBlackListDir(String blackListDir) {
            this.blackListDir = blackListDir;
            return this;
        }

        public TraceBuildConfig build() {
            return new TraceBuildConfig(packageName, mappingPath, baseMethodMap,
                    methodMapFile, ignoreMethodMapFile, blackListDir);
        }
    }

}
