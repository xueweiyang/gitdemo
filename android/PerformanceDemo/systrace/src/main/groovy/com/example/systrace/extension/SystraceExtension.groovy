package com.example.systrace.extension

class SystraceExtension {

    boolean enable
    String baseMethodMapFile
    String blackListFile
    String output

    SystraceExtension() {
        enable=true
        baseMethodMapFile=""
        blackListFile=""
        output=""
    }

}