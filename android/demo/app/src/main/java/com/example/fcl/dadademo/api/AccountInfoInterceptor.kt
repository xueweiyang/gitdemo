package com.example.fcl.dadademo.api

import com.example.fcl.dadademo.util.AccountManager
import okhttp3.Interceptor
import okhttp3.Interceptor.Chain
import okhttp3.Response

class AccountInfoInterceptor : Interceptor{
    override fun intercept(chain: Chain): Response {
        val account = AccountManager.account
        val request = chain.request()
            .newBuilder()
        account?.let {
            request.addHeader("authorization",account.token)
        }
        return chain.proceed(request.build())
    }
}