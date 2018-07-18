package com.example.fcl.dadademo.account.login

import com.example.fcl.dadademo.api.ApiService
import com.example.fcl.dadademo.util.AccountManager
import com.example.fcl.dadademo.util.RxSchedulerUtils
import com.example.fcl.dadademo.util.extension.subscribeRemoteData

class LoginPresenter(val view:LoginContract.View) : LoginContract.Presenter{
    override fun passwordLogin(mobile: String, password: String) {
        ApiService
            .passwordLogin(mobile, password)
            .compose(RxSchedulerUtils.ioToMainSchedulers())
            .subscribeRemoteData({account->
                account?.let {
                    AccountManager.login(account)
view.onPasswordLoginSucceed()
                }
            }, {e->
view.onRequestError(e)
            })
    }

    override fun subscribe() {
        view.bindPresenter(this)
    }

    override fun unSubscribe() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}