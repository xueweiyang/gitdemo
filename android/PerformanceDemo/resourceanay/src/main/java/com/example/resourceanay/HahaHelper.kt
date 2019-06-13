package com.example.resourceanay

import com.squareup.haha.perflib.ArrayInstance
import com.squareup.haha.perflib.ClassInstance
import com.squareup.haha.perflib.Instance
import com.squareup.haha.perflib.Type
import java.lang.Exception
import java.lang.IllegalArgumentException

object HahaHelper {

    fun threadName(holder: Instance): String {
        val values = classInstanceValues(holder)
        val nameField = fieldValue<String>(values, "name")
        return nameField
    }


    fun classInstanceValues(instance: Instance): List<ClassInstance.FieldValue> {
        return (instance as ClassInstance).values
    }

    fun <T> fieldValue(values: List<ClassInstance.FieldValue>, fieldName: String): T {
        values.forEach {
            if (fieldName == it.field.name) {
                return it.value as T
            }
        }
        throw IllegalArgumentException("field ${fieldName} does not exist")
    }

    fun isByteArray(value: Any): Boolean {
        return value is ArrayInstance && value.arrayType == Type.BYTE
    }

    fun getByteArray(arrayInstance: Any): ByteArray? {
        if (isByteArray(arrayInstance)) {
            try {
                val asRawByteArray =
                    ArrayInstance::class.java.getDeclaredMethod("asRawByteArray", Int.javaClass, Int.javaClass)
                asRawByteArray.isAccessible = true
                val length = ArrayInstance::class.java.getDeclaredField("mLength")
                length.isAccessible = true
                val lengthValue = length.get(arrayInstance)
                return asRawByteArray.invoke(arrayInstance, 0, lengthValue) as ByteArray
            } catch (e: Exception) {
                throw e
            }
        }
        return null
    }
}