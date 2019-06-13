package com.example.resourceanay

import android.graphics.Bitmap
import android.util.Log
import com.example.resourceanay.HahaHelper.fieldValue
import com.squareup.haha.perflib.ArrayInstance
import com.squareup.haha.perflib.ClassInstance
import com.squareup.haha.perflib.HprofParser
import com.squareup.haha.perflib.Instance
import com.squareup.haha.perflib.io.MemoryMappedFileBuffer
import java.util.*

object HeapAn {
    val TAG = "HeapAn"
    fun anay() {
        val dumpFile = AndroidHeapDumper().dumpHeap()
        if (!dumpFile.exists()) {
            Log.e(TAG, "no hprof file")
            return
        }
        val buffer = MemoryMappedFileBuffer(dumpFile)
        val parser = HprofParser(buffer)
        val snapshot = parser.parse()
        snapshot.computeDominators()

        val bitmapClasses = snapshot.findClasses(Bitmap::class.java.name)
        val heaps = snapshot.heaps
        val instanceList = arrayListOf<ArrayInstance>()

        computerDurationTime(ComputerState.START)

        for (heap in heaps) {
            if (heap.name != "app") {
                continue
            }
            for (clazz in bitmapClasses) {
                val instances = clazz.getHeapInstances(heap.id)
                val instanceSize= instances.size
                for (i in 0..instanceSize) {
                    val instance = instances[i]
                    if (instance.distanceToGcRoot == Int.MAX_VALUE) {
                        continue
                    }
                    val curHashCode = getHashCodeByInstance(instance)
                    for (j in i + 1..instanceSize) {
                        val curInstance = instances[j]
                        val nextHashCode = getHashCodeByInstance(curInstance)
                        if (curHashCode == nextHashCode) {
                            Log.e(TAG, "* stacks info")
                            val result = getAnalyzerResult(curInstance)
                            Log.e(TAG,result.toString())
                            getStackInfo(curInstance)
                            Log.e(TAG, "-------------------------------------------")
                            if (i == instanceSize-2 && j == instanceSize-1) {
                                Log.e(TAG, "* stacks info")
                                val result = getAnalyzerResult(curInstance)
                                Log.e(TAG,result.toString())
                                getStackInfo(curInstance)
                                Log.e(TAG, "-------------------------------------------")
                            }
                        }
                    }
                }
            }
        }
        computerDurationTime(ComputerState.END)
    }


    fun getStackInfo(instance: Instance) {
        if (instance.distanceToGcRoot != 0 && instance.distanceToGcRoot != Int.MAX_VALUE) {
            getStackInfo(instance.nextInstanceToGcRoot)
        }
        if (instance.nextInstanceToGcRoot != null) {
            Log.e(TAG, instance.nextInstanceToGcRoot.toString())
        }
    }

    fun getHashCodeByInstance(instance: Instance): Int {
        val classInstanceValues = (instance as ClassInstance).values
        val curBitmapBuffer = fieldValue<ArrayInstance>(classInstanceValues, "mBuffer")
        return Arrays.hashCode(curBitmapBuffer.values)
    }

    fun getAnalyzerResult(instance: Instance): AnalyzerResult {
        val classInstanceValues = (instance as ClassInstance).values
        val bitmapBuffer = fieldValue<ArrayInstance>(classInstanceValues, "mBuffer")
        val bitmapHeight = fieldValue<Int>(classInstanceValues, "mHeight")
        val bitmapWidth = fieldValue<Int>(classInstanceValues, "mWidth")
        return AnalyzerResult(
            Arrays.hashCode(bitmapBuffer.values),
            bitmapBuffer.toString(),
            bitmapBuffer.values.size,
            bitmapWidth,
            bitmapHeight
        )
    }

    enum class ComputerState {
        START, END
    }

    var startTime = 0L
    var endTime = 0L
    fun computerDurationTime(state: ComputerState) {
        if (state == ComputerState.START) {
            startTime = System.currentTimeMillis()
            Log.e(TAG, "开始时间----$startTime")
        } else {
            endTime = System.currentTimeMillis()
            Log.e(TAG, "结束时间----$endTime 耗时----${endTime - startTime}")
        }
    }

    class AnalyzerResult constructor(
        var hashCode: Int = 0,
        val classInstance: String = "",
        var width: Int = 0,
        var height: Int = 0,
        var bufferSize: Int = 0
    ) {


        override fun toString(): String {
            return "bufferHashCode:$hashCode\n" +
                    "width:$width\n" +
                    "height:$height\n" +
                    "bufferSize:$bufferSize"
        }
    }
}