package com.example.fcl.dadademo.api

import com.google.gson.Gson
import okhttp3.Interceptor
import okhttp3.Interceptor.Chain
import okhttp3.Response

class RpcResponseInterceptor : Interceptor {

    override fun intercept(chain: Chain): Response {
        val response = chain.proceed(chain.request())
        if (!response.isSuccessful) {
            analysisError(response)
        }
        return response
    }

    private fun analysisError(response: Response?) {
        response?.body()?.let { body ->
            if (body.contentType()?.toString()?.contains("json") == true) {
                when (response.code()) {
                    401 -> {
                        val errorResponse = Gson().fromJson(body.charStream(), UnauthorizedResponse::class.java)
                        throw UnauthorizedException(response.request().url().toString()+errorResponse.message)
                    }
                    500->{
                        val errorResponse=Gson().fromJson(body.charStream(),RpcErrorResponse::class.java)
                        throw RpcHttpException(errorResponse.code,errorResponse.message)
                    }
                    else -> Unit
                }
            }
        }
    }
}

data class UnauthorizedResponse(
    val message:String
)

data class RpcErrorResponse(
    val code:Long,
    val message: String
)