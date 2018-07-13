package com.example.fcl.dadademo.util.extension

import com.example.fcl.dadademo.api.NetworkErrorConsumer
import com.example.fcl.dadademo.api.RpcHttpException
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer

typealias SucceedCallback<T> = ((t:T)->Unit)
typealias FailedCallback=((e:RpcHttpException?)->Unit)

inline fun <T> Observable<T>.subscribeRemoteData(
    crossinline succeedConsumer:SucceedCallback<T?>,
    crossinline failedCallback: FailedCallback
) :Disposable{
    return this.subscribe(
        Consumer<T> {
            succeedConsumer.invoke(it)
        },
        object :NetworkErrorConsumer() {
            override fun handleFail(e: RpcHttpException?) {
                failedCallback.invoke(e)
            }
        }
    )
}