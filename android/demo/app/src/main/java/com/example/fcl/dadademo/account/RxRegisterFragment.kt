package com.example.fcl.dadademo.account

import android.app.Fragment
import android.content.Intent
import android.os.Bundle
import io.reactivex.subjects.ReplaySubject

class RxRegisterFragment: Fragment() {

    private var replaySubject : ReplaySubject<Boolean>?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance=true
    }

    fun setSubject(replaySubject: ReplaySubject<Boolean>) {
        this.replaySubject=replaySubject
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REGISTER_OR_LOGIN_REQUEST_CODE) {
            replaySubject?.onNext(true)
        } else{
            replaySubject?.onNext(false)
        }
        replaySubject?.onComplete()
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun startActivity(intent: Intent?) {
        startActivityForResult(intent, REGISTER_OR_LOGIN_REQUEST_CODE)
    }

    override fun startActivityForResult(intent: Intent?, requestCode: Int) {
        super.startActivityForResult(intent, requestCode)

    }

}

const val REGISTER_OR_LOGIN_REQUEST_CODE = 100