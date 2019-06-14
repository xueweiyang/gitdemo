package com.example.systrace;

import java.util.HashSet;

public class TraceBuildConfig {

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
