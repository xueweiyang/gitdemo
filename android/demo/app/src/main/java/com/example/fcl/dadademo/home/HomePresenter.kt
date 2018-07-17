package com.example.fcl.dadademo.home

class HomePresenter(val view:HomeContract.View):HomeContract.Presenter{
    override fun subscribe() {
        loadLocalFoundation()
    }

    private fun loadLocalFoundation() {

    }

    fun fetchFoundation() {

    }

    override fun unSubscribe() {

    }
}