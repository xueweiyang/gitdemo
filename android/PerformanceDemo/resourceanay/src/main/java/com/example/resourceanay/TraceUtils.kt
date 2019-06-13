package com.example.resourceanay

import com.squareup.haha.perflib.Instance

object TraceUtils {

    fun getTraceFromInstance(instance: Instance): List<Instance> {
        val list = arrayListOf<Instance>()
        var nextInstance = instance.nextInstanceToGcRoot
        while (nextInstance != null) {
            list.add(nextInstance)
            nextInstance = nextInstance.nextInstanceToGcRoot
        }
        return list
    }

}