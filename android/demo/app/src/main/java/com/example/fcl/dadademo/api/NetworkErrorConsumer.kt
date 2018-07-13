package com.example.fcl.dadademo.api

import com.example.fcl.dadademo.util.ToastHelper
import io.reactivex.functions.Consumer

abstract class NetworkErrorConsumer : Consumer<Throwable> {
    override fun accept(t: Throwable) {
        t.printStackTrace()
        when (t) {
            is UnauthorizedException->{
                ToastHelper.toast("账号已失效，请重新登录")

            }
            is RpcHttpException->{
                handleFail(t)
            }
            else -> {
                handleFail(null)
                ToastHelper.toast("网络连接失败，请稍后重试")
            }
        }
    }

    abstract fun handleFail(e:RpcHttpException?)
}