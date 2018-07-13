package com.example.fcl.dadademo.api

import okhttp3.Interceptor
import okhttp3.Interceptor.Chain
import okhttp3.Response

class ContentTypeInterceptor : Interceptor {
    override fun intercept(chain: Chain): Response {
        var request = chain.request()
        if (request.method().equals("post", true)) {
            request = request.newBuilder()
                .header("Content-Type", "application/json;charset=UTF-8")
                .build()
        }
        return chain.proceed(request)
    }
}