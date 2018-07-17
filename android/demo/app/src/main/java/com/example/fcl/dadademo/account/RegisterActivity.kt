package com.example.fcl.dadademo.account

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.fcl.dadademo.base.BaseActivity
import com.example.fcl.kotlindemo.R
import kotlinx.android.synthetic.main.layout_register_activity.sendVerifyView

class RegisterActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_register_activity)
        initView()
    }

    private fun initView() {
        sendVerifyView.setOnClickListener{

        }
    }


}