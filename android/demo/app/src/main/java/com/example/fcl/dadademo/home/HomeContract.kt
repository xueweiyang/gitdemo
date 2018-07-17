package com.example.fcl.dadademo.home

import com.example.fcl.dadademo.mvp.BasePresenter
import com.example.fcl.dadademo.mvp.BaseView

interface HomeContract{

    interface View :BaseView<Presenter> {

    }

    interface Presenter : BasePresenter{

    }
}