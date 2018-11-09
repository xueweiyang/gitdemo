package com.fcl

import org.gradle.BuildListener
import org.gradle.BuildResult
import org.gradle.api.Task
import org.gradle.api.execution.TaskExecutionListener
import org.gradle.api.initialization.Settings
import org.gradle.api.invocation.Gradle
import org.gradle.api.tasks.TaskState

/**
 * Created by galio.fang on 18-11-9*/
class TimingListener implements TaskExecutionListener,BuildListener {

    private Clock clock
    private timings = []

    @Override
    void buildStarted(Gradle gradle) {}

    @Override
    void settingsEvaluated(Settings settings) {}

    @Override
    void projectsLoaded(Gradle gradle) {}

    @Override
    void projectsEvaluated(Gradle gradle) {}

    @Override
    void buildFinished(BuildResult buildResult) {
        println "Task timings:"
        for (timing in timings) {
            if (timing[0] >= 50) {
                printf "%7sms %s\n", timing
            }
        }
    }

    @Override
    void beforeExecute(Task task) {
        clock = new Clock()
    }

    @Override
    void afterExecute(Task task, TaskState taskState) {
        def ms = clock.getTime()
        timings.add([ms, task.path])
        task.project.logger.warn "${task.path} took ${ms}ms"

    }
}
