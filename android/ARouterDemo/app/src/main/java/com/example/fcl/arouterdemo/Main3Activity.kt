package com.example.fcl.arouterdemo

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route

import kotlinx.android.synthetic.main.activity_main2.*

@Route(path = "/com1/Main2Activity")
class Main3Activity : AppCompatActivity() {

//    @Autowired
    val name: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
    }
}
