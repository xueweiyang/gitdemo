package com.example.fcl.dadademo.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import com.example.fcl.dadademo.account.RegisterActivity
import com.example.fcl.dadademo.base.BaseFragment
import com.example.fcl.kotlindemo.R
import kotlinx.android.synthetic.main.layout_profile_fragment.loginView

class ProfileFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.layout_profile_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpView()
    }

    private fun setUpView() {
        loginView.setOnClickListener(object : OnClickListener{
            override fun onClick(v: View?) {
                startActivity(Intent(context, RegisterActivity::class.java))
            }
        })
    }
}