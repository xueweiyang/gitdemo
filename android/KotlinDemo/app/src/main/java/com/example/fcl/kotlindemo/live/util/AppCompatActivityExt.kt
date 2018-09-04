package com.example.fcl.kotlindemo.live.util

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import com.example.fcl.kotlindemo.live.ViewModelFactory

/**
 * Created by galio.fang on 18-9-4
 */
fun <T:ViewModel> AppCompatActivity.obtainViewModel(viewModelClass: Class<T>)=
    ViewModelProviders.of(this, ViewModelFactory.)
