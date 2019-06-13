package com.example.performancedemo

import android.Manifest
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.resourceanay.HeapAn
import com.example.resourceanay.HeaperAnayler
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        image1.setBackgroundResource(R.mipmap.ic_launcher)
        image2.setBackgroundResource(R.mipmap.ic_launcher)

        RxPermissions(this)
            .request(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            .subscribe({
//                HeaperAnayler.anaylaze()
                HeapAn.anay()
            }, {})
    }
}
