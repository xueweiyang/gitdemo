package com.fcl

import org.gradle.api.Plugin
import org.gradle.api.Project

public class HelloPlugin implements Plugin<Project>{
    @Override
    void apply(Project project) {

        project.extensions.create('helloconf', HelloExtension)

        project.task('testhelloTask')<<{
            println('hello plugin')

            Closure nameMap = project['helloconf'].nameMap
            String destDir = project['helloconf'].destDir

            project.android.applicationVariants.all { variant ->
                variant.outputs.all {
                    outputFileName = "hello.apk"
                }
            }
        }
    }
}