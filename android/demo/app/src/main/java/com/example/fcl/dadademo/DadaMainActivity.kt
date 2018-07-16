package com.example.fcl.dadademo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import android.view.View.OnClickListener
import com.example.fcl.dadademo.home.ContentAdapter
import com.example.fcl.dadademo.home.HomeFragment
import com.example.fcl.dadademo.util.RegisterHelper
import com.example.fcl.kotlindemo.R
import kotlinx.android.synthetic.main.activity_dada_main.homePager
import kotlinx.android.synthetic.main.activity_dada_main.homeTab

class DadaMainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dada_main)
        initView()
    }

    private fun initView() {
        val fragments = ArrayList<Fragment>(4)
        fragments.add(HomeFragment())
        fragments.add(HomeFragment())
        fragments.add(HomeFragment())
        fragments.add(HomeFragment())
        fragments.add(HomeFragment())
        val titles = resources.getStringArray(R.array.homeTitles)
        val adapter = ContentAdapter(supportFragmentManager, fragments, titles)
        homePager.adapter = adapter
        homeTab.setupWithViewPager(homePager)
        for (index in 0..(homeTab.childCount - 1)) {
            val tab = homeTab.getTabAt(index)
            val tabView = tab?.customView?.parent as? View
            tabView?.setOnClickListener(object : OnClickListener {
                override fun onClick(v: View?) {
                    if (index == TAB_CURRICULUM || index == TAB_RESERVATION || index == TAB_SERVICE) {
                        val entranceName: String =
                            when (index) {
                                TAB_CURRICULUM -> {
                                    "tab页-课表"
                                }
                                TAB_RESERVATION -> {
                                    "tab页-约课"
                                }
                                TAB_SERVICE -> {
                                    "tab页-服务"
                                }
                                else -> {
                                    "tab页-调起"
                                }
                            }
                        RegisterHelper().register(
                            this@DadaMainActivity,
                            entranceName = entranceName
                        ).subscribe({
                            if (it) {

                            }
                        })
                    }
                }
            })
        }
    }
}
