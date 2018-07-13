package com.example.fcl.dadademo.api

import com.google.gson.Gson
import com.google.gson.TypeAdapter
import com.google.gson.TypeAdapterFactory
import com.google.gson.reflect.TypeToken
import kotlin.reflect.KClass

class KotlinAdapterFactory : TypeAdapterFactory {

    private fun Class<*>.isKotlinClass(): Boolean {
        return this.declaredAnnotations.any {
            it.annotationClass.qualifiedName == "kotlin.Metadata"
        }
    }

    override fun <T : Any> create(gson: Gson, type: TypeToken<T>): TypeAdapter<T>? {
        return if (type.rawType.isKotlinClass()) {
            val kClass = (type.rawType as Class<*>).kotlin
            val delegateAdapter = gson.getDelegateAdapter(this, type)
            KotlinAdapter<T>(delegateAdapter, kClass as KClass<T>)
        } else {
            null
        }
    }
}