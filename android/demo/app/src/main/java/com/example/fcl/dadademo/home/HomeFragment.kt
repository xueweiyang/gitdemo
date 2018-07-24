package com.example.fcl.dadademo.home

import android.app.Activity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import com.example.fcl.dadademo.home.HomeContract.Presenter
import com.example.fcl.dadademo.model.Ad
import com.example.fcl.dadademo.model.FreePractice
import com.example.fcl.dadademo.model.HomeFoundation
import com.example.fcl.dadademo.util.AccountManager
import com.example.fcl.dadademo.util.ActivityManager
import com.example.fcl.dadademo.util.RegisterHelper
import com.example.fcl.dadademo.util.ToastHelper
import com.example.fcl.kotlindemo.R
import kotlinx.android.synthetic.main.layout_home_fragment.adBannerView
import kotlinx.android.synthetic.main.layout_home_fragment.homeFreePracticeLayout
import kotlinx.android.synthetic.main.layout_home_fragment.homeFreePracticeView

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
        homePresenter.fetchFoundation()
    }

    override fun bindPresenter(presenter: Presenter) {

    }

    private fun initView() {
    }

    override fun showFoundation(homeFoundation: HomeFoundation) {
        setBannerAds(homeFoundation.bannerAds)
        setupFreePractice(homeFoundation.freePractice)
    }

    private fun setupFreePractice(freePractice: FreePractice?){
        if (freePractice!=null&&freePractice.items.isNotEmpty()){
            homeFreePracticeLayout.title=freePractice.title
            homeFreePracticeView.setupFreePractice(freePractice.items)
            homeFreePracticeView.freePracticeClickCallback = {
                ActivityManager.navigateToH5(it.jumpUrl)
            }
        }
    }

    private fun setBannerAds(bannerAds: List<Ad>) {
        if (bannerAds.isNotEmpty()){
            val bannerAdapter=HomeBannerAdapter(activity, bannerAds)
            adBannerView.adapter=bannerAdapter
        }
    }
}