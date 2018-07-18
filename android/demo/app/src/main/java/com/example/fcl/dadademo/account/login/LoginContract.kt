package com.example.fcl.dadademo.account.login

import com.example.fcl.dadademo.api.RpcHttpException
import com.example.fcl.dadademo.mvp.BasePresenter
import com.example.fcl.dadademo.mvp.BaseView

interface LoginContract {

    interface View : BaseView<Presenter> {
        fun onPasswordLoginSucceed()
        fun onRequestError(e: RpcHttpException?)
    }

    interface Presenter : BasePresenter {
        fun passwordLogin(mobile: String, password: String)
    }
}