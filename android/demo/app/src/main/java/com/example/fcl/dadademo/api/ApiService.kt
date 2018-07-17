package com.example.fcl.dadademo.api

import com.example.fcl.dadademo.model.HomeFoundation
import com.example.fcl.dadademo.model.ImageVerify
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

    /**
     * 获取图片验证码
     */
    fun getImageCaptcha(width:Int,height:Int):Observable<ImageVerify>{
        val requestBody= mapOf(
                "width" to width,
                "height" to height
        )
        return normalApiImpl.getImageCaptcha(requestBody)
    }

    private interface Impl {

        /**
         * 获取图片验证码
         */
        @POST("v1/common.captcha.get")
        fun getImageCaptcha(@Body map: Map<String, @JvmSuppressWildcards Any>): Observable<ImageVerify>


        @POST("v1/student.allInOne.advertList")
        fun getAdvertData(@Body map: Map<String, @JvmSuppressWildcards Any>): Observable<SplashFoundation>

        /**
         * 获取首页数据
         */
        @POST("v1/student.allInOne.homepage2")
        fun fetchHomeFoundation(@Body map: Map<String, Int?>): Observable<HomeFoundation>
    }
}