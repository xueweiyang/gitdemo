package com.example.fcl.dadademo.mvp

interface BaseView<in T> {

    fun bindPresenter(presenter: T)
}