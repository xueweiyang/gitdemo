package com.example.andresguard

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

/**
 * 1、拷贝资源文件
 * 2、建立混淆对应关系
 * 3、修改resources.arsc文件
 * 4、生成apk
 */
class AndResGuardTask extends DefaultTask {
    String TAG = "AndResGuardTask"

    AndResGuardTask() {
        group = "andresguard"
    }

    @TaskAction
    run() {
        Log.i(TAG, "-------configuration--------")
        copyRes()
//        AndResTask.doTask()
        AndRes.doTask()
    }

    def copyRes() {
        def srcDir = "${project.rootDir}/${project.name}/src/main/res/"
        new File(srcDir).eachDir { dir ->
            dir.eachFile { file ->
                def destPath = file.path.replace("src/main", "build/outputs/apk/andresguard/temp")
                copyFile(file, new File(destPath))
            }
        }
    }

    static def copyFile(File src, File to, boolean deleteSrc = false) {
//        println "copyFile[${src} to ${to}]"
        to.parentFile.mkdirs()
        if (src.name.endsWith(".png") || src.name.endsWith(".jpeg") || src.name.endsWith(".jpg")) {
            to << src.bytes
        } else {
            to << src.text
        }
        if (deleteSrc) {
            if (src.delete()) {
                println "delete src[${src}"
            }
        }
    }

}