package com.example.andresguard

import org.gradle.api.DefaultTask
import org.gradle.api.Task
import org.gradle.api.tasks.TaskAction

class AndResGuardTask extends DefaultTask {
String TAG="AndResGuardTask"
    AndResGuardTask() {
        group = "andresguard"
    }

    @TaskAction
    run() {
       Log.i(TAG, "-------configuration--------")
    }

}