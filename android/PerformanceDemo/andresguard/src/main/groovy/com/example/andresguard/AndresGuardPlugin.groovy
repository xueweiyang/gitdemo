package com.example.andresguard

import org.gradle.api.Plugin
import org.gradle.api.Project

class AndresGuardPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        project.afterEvaluate {
            def task = project.task("resguardRelease", type:AndResGuardTask)
            task.dependsOn "assembleRelease"
        }
    }
}