package com.example.resourceanay

import android.util.Log
import com.squareup.haha.perflib.ArrayInstance
import com.squareup.haha.perflib.HprofParser
import com.squareup.haha.perflib.Instance
import com.squareup.haha.perflib.Snapshot
import com.squareup.haha.perflib.io.MemoryMappedFileBuffer
import java.util.*
import kotlin.collections.ArrayList

object HeaperAnayler {
val TAG = "HeaperAnayler"
    fun anaylaze(){
        val dumpFile = AndroidHeapDumper().dumpHeap()
        if (!dumpFile.exists()) {
            Log.e(TAG, "no hprof file")
            return
        }
        val buffer=MemoryMappedFileBuffer(dumpFile)
        val parser = HprofParser(buffer)
        val snapshot=parser.parse()
        snapshot.computeDominators()

        val bitmapClass = snapshot.findClass("android.graphics.Bitmap")

        val defaultHeap = snapshot.getHeap("default")
        val appHeap = snapshot.getHeap("app")
        val defaultBmInstance=bitmapClass.getHeapInstances(defaultHeap.id)
        val appBmInstance = bitmapClass.getHeapInstances(appHeap.id)

        defaultBmInstance.addAll(appBmInstance)

        val collectInfos =collectSameBitmap(snapshot,defaultBmInstance)
        collectInfos.forEach {
            Log.e(TAG, it.string())
        }
    }

    fun collectSameBitmap(snapshot:Snapshot,instanceList:List<Instance>) :List<DumplicatedCollectInfo>{
        val collectSameMap = hashMapOf<String,List<Instance>>()
        val duplicatedCollectInfos = arrayListOf<DumplicatedCollectInfo>()

        instanceList.forEach {
            val classFieldList = HahaHelper.classInstanceValues(it)
            val arrayInstance = HahaHelper.fieldValue<ArrayInstance>(classFieldList, "mBuffer")
            val bufferByte = HahaHelper.getByteArray(arrayInstance)
            val bufferHashCode = Arrays.hashCode(bufferByte)
            val hashKey = bufferHashCode.toString()

            if (collectSameMap.containsKey(hashKey)){
                (collectSameMap.get(hashKey) as ArrayList).add(it)
            } else {
                val list = arrayListOf<Instance>()
                list.add(it)
                collectSameMap.put(hashKey, list)
            }
        }

        collectSameMap.forEach { entry->
//            if (entry.value.size > 1) {
                val info = DumplicatedCollectInfo(entry.key)
                entry.value.forEach {instance->
                    info.addBitmapInstance(BitmapInstance(snapshot, entry.key, instance))
                }
                info.internalSetValue()
                duplicatedCollectInfos.add(info)
//            }
        }

        return duplicatedCollectInfos
    }
}