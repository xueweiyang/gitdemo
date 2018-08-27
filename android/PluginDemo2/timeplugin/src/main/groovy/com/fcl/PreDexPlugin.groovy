package com.fcl

import org.gradle.api.Plugin
import org.gradle.api.Project

public class PreDexPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        println("apply predex")
    }
}