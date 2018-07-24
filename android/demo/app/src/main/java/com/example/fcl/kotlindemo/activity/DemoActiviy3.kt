package com.example.fcl.kotlindemo.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.Toolbar
import com.example.fcl.kotlindemo.R
import com.example.fcl.kotlindemo.adapter.ResultAdapter
import com.example.fcl.kotlindemo.view.SearchCardView

class DemoActiviy3 : KotlinBaseActivity() {
    private var toolbar: Toolbar? = null
    private var recyclerView: RecyclerView? = null
    private var searchView: SearchCardView? = null
    private var resultAdapter: ResultAdapter? = null
    private var et: EditText? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demo)
        initView()
    }

    private fun initView() {
        toolbar = findViewById(R.id.toolbar)
        recyclerView = findViewById(R.id.resultRecycler)
        searchView = findViewById(R.id.searchCard)
        et = searchView!!.getEt()
        setRecycler()
        setSearch()
    }

    private fun setSearch() {
        et!!.setText("kotlin")
        et!!.setSelection(et!!.text.length)
        et!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
            }

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
            }

            override fun afterTextChanged(editable: Editable) {
            }
        })
        et!!.setOnClickListener { }
    }

    private fun setRecycler() {
        resultAdapter = ResultAdapter(this)
        recyclerView!!.adapter = resultAdapter
        recyclerView!!.layoutManager = LinearLayoutManager(this)
    }
}
