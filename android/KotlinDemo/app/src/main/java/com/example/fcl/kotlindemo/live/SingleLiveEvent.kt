package com.example.fcl.kotlindemo.live

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.support.annotation.MainThread
import android.util.Log
import java.util.concurrent.atomic.AtomicBoolean

/**
 * Created by galio.fang on 18-9-5
 */
class SingleLiveEvent<T> : MutableLiveData<T>() {
    val TAG=SingleLiveEvent::class.simpleName
    private val pending = AtomicBoolean(false)

    override fun observe(owner: LifecycleOwner, observer: Observer<T>) {
        if (hasActiveObservers()) {
            Log.e(TAG, "Multiple observers registered but only one will be notified of changes.")
        }
        super.observe(owner, Observer<T> {t->
            if (pending.compareAndSet(true,false)) {
                observer.onChanged(t)
            }
        })
    }
@MainThread
    override fun setValue(t: T?) {
        pending.set(true)
        super.setValue(t)
    }

    @MainThread
fun call() {
        value=null
    }
}
