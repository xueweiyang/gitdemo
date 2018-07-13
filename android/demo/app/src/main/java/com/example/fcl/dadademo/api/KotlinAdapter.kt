package com.example.fcl.dadademo.api

import com.google.gson.JsonParseException
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import kotlin.reflect.KClass
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.jvm.isAccessible

class KotlinAdapter<T : Any>(
    private val delegateAdapter: TypeAdapter<T>,
    private val kClass: KClass<T>
) : TypeAdapter<T>() {

    override fun read(`in`: JsonReader?): T? {
        return delegateAdapter.read(`in`)?.apply {
            nullCheck(this)
        }
    }

    override fun write(out: JsonWriter?, value: T) {
        delegateAdapter.write(out, value)
    }

    private fun nullCheck(value :T){
        kClass.declaredMemberProperties.forEach { prop->
            prop.isAccessible=true
            if (!prop.returnType.isMarkedNullable && prop(value) == null) {
                throw JsonParseException(
                    "Field: '${prop.name}' in Class '${kClass.java.name}' is marked nonnull but found null value"
                )
            }
        }
    }
}