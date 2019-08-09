package com.example.interview.rxjava

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.interview.R
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_rxjava.*

class RxjavaActivity : AppCompatActivity() {
    val TAG = "RxjavaActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rxjava)
        test.setOnClickListener {
            create()
        }
    }

    fun create() {
        Observable.create(ObservableOnSubscribe<String> {
            it.onNext("s")
            it.onComplete()
        })
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe({
            Log.e(TAG, "next:$it")
        }, {}, {
            Log.e(TAG, "complete")
        })
    }
}
