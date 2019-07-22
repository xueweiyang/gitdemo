package com.example.resourceanay

import com.squareup.haha.perflib.ArrayInstance
import com.squareup.haha.perflib.ClassObj
import com.squareup.haha.perflib.Instance
import com.squareup.haha.perflib.Snapshot

class BitmapInstance constructor(
    snapshot: Snapshot,
    hash: String,
    instance: Instance
) {

    val traceStack = arrayListOf<Instance>()
    var width=0
    var height=0
    var size=0

    init {
        traceStack.addAll(TraceUtils.getTraceFromInstance(instance))
        val classFieldList =HahaHelper.classInstanceValues(instance)
        width=HahaHelper.fieldValue(classFieldList,"mWidth")
        height=HahaHelper.fieldValue(classFieldList,"mHeight")

        val arrayInstance = HahaHelper.fieldValue<ArrayInstance>(classFieldList,"mBuffer")
        val bufferByte = HahaHelper.getByteArray(arrayInstance)
        if (bufferByte!= null){
            size = bufferByte.size
        }
    }

    fun getTrace() :String {
        val builder=StringBuilder()
        builder.append("[")
        traceStack.forEachIndexed { index, instance ->
            val className = if (instance is ClassObj) {
                instance.className
            } else {
                instance.classObj.className
            }
            builder.append("\"").append(className).append("\"")
            if (index != traceStack.size - 1) {
                builder.append(",")
            } else {
                builder.append("]")
            }
            builder.append("\n")
        }
        return builder.toString()
    }

    fun getTraceFromLeakCanary():String{
        return ""
    }

}