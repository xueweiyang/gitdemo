package com.fcl

import com.android.build.gradle.AppPlugin
import org.gradle.api.Plugin
import org.gradle.api.Project
import com.android.build.gradle.AppExtension

public class TimePlugin implements Plugin<Project> {
    @Override
    void apply(Project project) {
        println("TimePlugin:apply")
        def android = project.extensions.getByType(AppExtension)
        android.registerTransform(new TimeTransform(project))

//        project.extensions.create("testCreateJavaConfig",MyPluginTestClass)
//        if (project.plugins.hasPlugin(AppPlugin)) {
//            android.applicationVariants.all { variant->
//                def variantData = variant.variantData
//                def scope = variantData.scope
//
//                def config = project.extensions.getByName("testCreateJavaConfig")
//
//                def createTaskName = scope.getTaskName("ceshi", "MyTestPlugin")
//                def createTask = project.task(createTaskName)
//                println("create class")
////                createTask.doLast {
//                    createJavaTest(variant, config)
////                }
//
//            }
//        }
    }

//    def void createJavaTest(variant, config) {
//        println("create")
//        def content = """package com.example.fcl.plugindemo2;
//public class MyPluginTestClass {
//    public static final String str = "test";
//}"""
//    File outputDir = variant.getVariantData().getScope().getBuildConfigSourceOutputDir()
//        def javaFile = new File(outputDir, "MyPluginTestClass.java")
//        javaFile.write(content, 'UTF-8')
//    }
}

