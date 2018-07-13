package com.example.fcl.dadademo.api

import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit.SECONDS

object RetrofitFactory {

    private val defaultGson = GsonBuilder()
        .registerTypeAdapterFactory(KotlinAdapterFactory())
        .create()

    @JvmOverloads
    fun <T> createService(
        service: Class<T>,
        baseUrl: String,
        gson: Gson = defaultGson,
        config: RetrofitConfig? = null
    ): T {
        val httpBuilder = generateHttpBuilder()
        config?.invoke(httpBuilder)
        val client = httpBuilder.build()
        val retrofit = createAdapter(client,baseUrl, gson)
        return retrofit.create(service)
    }

    private fun generateHttpBuilder() : OkHttpClient.Builder {
        val builder = OkHttpClient.Builder()
            .connectTimeout(30, SECONDS)
            .readTimeout(30,SECONDS)
            .writeTimeout(30,SECONDS)
        if (InitContentProvider.param.isDebug) {
            builder.addNetworkInterceptor(StethoInterceptor())
        }
        return builder
    }

    private fun createAdapter(client: OkHttpClient,baseUrl: String,gson: Gson= defaultGson):Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }
}

typealias RetrofitConfig = ((OkHttpClient.Builder) -> Unit)