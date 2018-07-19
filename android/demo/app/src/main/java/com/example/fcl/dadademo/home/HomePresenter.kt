package com.example.fcl.dadademo.home

import com.example.fcl.dadademo.api.ApiService
import com.example.fcl.dadademo.util.AccountManager
import com.example.fcl.dadademo.util.RxSchedulerUtils
import com.example.fcl.dadademo.util.extension.subscribeRemoteData

class HomePresenter(val view:HomeContract.View):HomeContract.Presenter{
    override fun subscribe() {
        loadLocalFoundation()
    }

    private fun loadLocalFoundation() {

    }

    fun fetchFoundation() {
        ApiService.fetchHomeFoundation(AccountManager.account?.isVip)
            .compose(RxSchedulerUtils.ioToMainSchedulers())
            .subscribeRemoteData({data->
                data?.let {
                    view.showFoundation(it)
                }
            }, {e->

            })
    }

    override fun unSubscribe() {

    }
}