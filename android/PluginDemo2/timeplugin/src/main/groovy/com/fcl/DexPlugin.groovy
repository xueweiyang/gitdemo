package com.fcl

import com.android.build.gradle.AppExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

public class DexPlugin implements Plugin<Project> {
    @Override
    void apply(Project project) {
        println("DexPlugin:apply")
        project.afterEvaluate {
            project.android.applicationVariants.each { variant ->
                def preDexTask = project.tasks.findByName("preDex${variant.name.capitalize()}")
                def dexTask = project.tasks.findByName("dex${variant.name.capitalize()}")

                if (preDexTask) {
                    println("predextask:"+preDexTask)
                    Set<File> preDexTaskInputFiles = preDexTask.inputs.files.files

                    println("Name:preDexTaskInputFiles====>${preDexTask.name}")
                } else {
                    println("predextask nonono:"+preDexTask)
                }

                if (dexTask) {
                    println("Name:dexTaskInputFiles=====>${dexTask.name}")
                } else {
                    println("Name:dexTaskInputFiles nonono=====>")
                }
            }
        }
    }

}

