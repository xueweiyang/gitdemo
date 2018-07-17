package com.example.fcl.dadademo.home

import android.app.Activity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import com.example.fcl.dadademo.home.HomeContract.Presenter
import com.example.fcl.dadademo.util.RegisterHelper
import com.example.fcl.kotlindemo.R
import kotlinx.android.synthetic.main.layout_home_fragment.checkLogin

class HomeFragment : Fragment(),HomeContract.View {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.layout_home_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        setUpPresenter()
    }

    private fun setUpPresenter() {
        val homePresenter=HomePresenter(this)
        bindPresenter(homePresenter)
    }

    override fun bindPresenter(presenter: Presenter) {

    }

    private fun initView() {
        checkLogin.setOnClickListener(object :OnClickListener{
            override fun onClick(v: View?) {
                RegisterHelper().register(
                    activity as Activity,
                    entranceName = "首页"
                ).subscribe({
                    if (it) {

                    }
                })
            }
        })
    }
}