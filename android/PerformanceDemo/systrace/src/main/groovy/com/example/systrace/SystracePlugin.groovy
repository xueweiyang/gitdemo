package com.example.systrace

import com.example.systrace.extension.SystraceExtension
import com.example.systrace.transform.SystemTraceTransform
import org.gradle.api.GradleException
import org.gradle.api.Plugin
import org.gradle.api.Project

class SystracePlugin implements Plugin<Project> {

    String TAG = "SystracePlugin"

    @Override
    void apply(Project project) {
        project.extensions.create("systrace", SystraceExtension)

        if (!project.plugins.hasPlugin('com.android.application')) {
            throw new GradleException('systrace plugin need android application plugin')
        }

        project.afterEvaluate {
            def android = project.extensions.android
            def configuration = project.systrace
            android.applicationVariants.all { variant ->
                String output = configuration.output
                if (Util.isNullOrNil(output)) {
                    configuration.output = project.getBuildDir().getAbsolutePath() + File.separator + "systrace_output"
                    Log.i(TAG, "set systrace output file to " + configuration.output)
                }
                if (configuration.enable) {
                    SystemTraceTransform.inject(project, variant)
                }
            }
        }
    }
}