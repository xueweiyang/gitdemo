package com.example.fcl.dadademo.util

import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

object RxSchedulerUtils{

    fun <T> ioToMainSchedulers():ObservableTransformer<T,T>{
        return ObservableTransformer {
            it.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        }
    }

    inline fun <T> ioToMainSchedulers(crossinline action:(dis:Disposable)->Unit) : ObservableTransformer<T,T> {
        return ObservableTransformer {
            it.subscribeOn(Schedulers.io())
                .doOnSubscribe {
                    action.invoke(it)
                }
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
        }
    }

}