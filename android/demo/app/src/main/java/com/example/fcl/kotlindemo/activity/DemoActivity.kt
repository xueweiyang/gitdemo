package com.example.fcl.kotlindemo.activity

import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import com.example.fcl.kotlindemo.R
import com.example.fcl.kotlindemo.adapter.ResultAdapter
import kotlinx.android.synthetic.main.activity_demo.resultRecycler
import kotlinx.android.synthetic.main.activity_demo.searchCard
import kotlinx.android.synthetic.main.activity_main.toolbar

class DemoActivity : BaseActivity() {

    lateinit var resultAdapter : ResultAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demo)

        setSupportActionBar(toolbar)
        setRecyclerView()
        setSearchView()
    }

    private fun setSearchView() {
        val searchEt = searchCard.getEt()
        searchEt.setText("kotlin")
        searchEt.setSelection(searchEt.text.length)
        searchEt.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })
    }

    private fun setRecyclerView() {
        resultAdapter = ResultAdapter(this)
        resultRecycler.adapter = resultAdapter
        resultRecycler.layoutManager = LinearLayoutManager(this)
    }
}