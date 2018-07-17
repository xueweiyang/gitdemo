package com.example.fcl.dadademo.api

import com.example.fcl.dadademo.model.HomeFoundation
import com.example.fcl.dadademo.model.SplashFoundation
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST

object ApiService {

    private val normalApiImpl: Impl
    private val accountApiImpl: Impl

    init {
        normalApiImpl = RetrofitFactory.createService(Impl::class.java, Constant.API_DOMAIN) { builder ->
            builder.addInterceptor(ContentTypeInterceptor())
                .addInterceptor(RpcResponseInterceptor())
        }
        accountApiImpl = RetrofitFactory.createService(Impl::class.java, Constant.API_DOMAIN) { builder ->
            builder.addInterceptor(ContentTypeInterceptor())
                .addInterceptor(RpcResponseInterceptor())
                .addInterceptor(AccountInfoInterceptor())
        }
    }

    /**
     * 获取广告列表
     */
    fun getAdvertData(appType: Int, adType: Int, isVip: Int? = null): Observable<SplashFoundation> {
        val requestBody = mutableMapOf(
            "app_type" to appType,
            "ad_type" to adType
        )
        isVip?.let {
            requestBody["s_is_vip"] = it
        }
        return normalApiImpl.getAdvertData(requestBody)
    }

    /**
     * 首页数据
     */
    fun fetchHomeFoundation(isVip: Int?=0):Observable<HomeFoundation>{
        val requestBody = mapOf(
            "s_is_vip" to isVip,
            "app_type" to 1
        )
        return accountApiImpl.fetchHomeFoundation(requestBody)
    }

    private interface Impl {

        @POST("v1/student.allInOne.advertList")
        fun getAdvertData(@Body map: Map<String, @JvmSuppressWildcards Any>): Observable<SplashFoundation>

        /**
         * 获取首页数据
         */
        @POST("v1/student.allInOne.homepage2")
        fun fetchHomeFoundation(@Body map: Map<String, Int?>): Observable<HomeFoundation>
    }
}