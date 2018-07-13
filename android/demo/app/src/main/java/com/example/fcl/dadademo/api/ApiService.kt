package com.example.fcl.dadademo.api

import com.example.fcl.dadademo.model.SplashFoundation
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST

object ApiService{

    private val normalApiImpl : Impl

    init {
        normalApiImpl = RetrofitFactory.createService(Impl::class.java, Constant.API_DOMAIN) { builder ->
            builder.addInterceptor(ContentTypeInterceptor())
                .addInterceptor(RpcResponseInterceptor())
        }
    }

    /**
     * 获取广告列表
     */
    fun getAdvertData(appType : Int, adType:Int, isVip:Int?=null):Observable<SplashFoundation> {
        val requestBody = mutableMapOf(
            "app_type" to appType,
            "ad_type" to adType
        )
        isVip?.let {
            requestBody["s_is_vip"] = it
        }
return normalApiImpl.getAdvertData(requestBody)
    }

    private interface Impl{

        @POST("v1/student.allInOne.advertList")
        fun getAdvertData(@Body map:Map<String, @JvmSuppressWildcards Any>) : Observable<SplashFoundation>


    }
}