package com.example.fcl.dadademo.web

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.webkit.URLUtil
import com.example.fcl.dadademo.base.BaseActivity
import com.example.fcl.dadademo.util.AccountManager
import com.example.fcl.dadademo.util.Constant
import com.example.fcl.dadademo.util.ToastHelper
import com.example.fcl.dadademo.widget.WebViewProgressBar
import com.example.fcl.kotlindemo.R
import kotlinx.android.synthetic.main.activity_h5.swipeChild
import kotlinx.android.synthetic.main.view_normal_toolbar.dadaToolbar

open class WebBearerActivity : BaseActivity(),JsBridgeDelegate {


    private lateinit var progressBar:WebViewProgressBar
    protected var dadaWebView:DadaWebView?=null
    var url:String?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_h5)
        initConfig()
        initView()
        intent?.let {
            url = it.getStringExtra(Constant.WEB_VIEW_URL_EXTRA)
        }
        loadUrl(url)
    }

    fun loadUrl(url:String?){
        url?.let {
            if (URLUtil.isNetworkUrl(url)) {
                val account=AccountManager.account
                val heads = mutableMapOf<String,String>()
                account?.let {
                    heads["authorization"] = account.token
                }
                dadaWebView?.loadUrl(url,heads)
            }
        }
    }

    private fun initConfig() {
        intent?.let {
            val landscape = it.getBooleanExtra(Constant.WEB_VIEW_LANDSCAPE_EXTRA, false)
            requestedOrientation=if (landscape){
                ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE
            } else{
                ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            }
            val hasNavBar=it.getBooleanExtra(Constant.WEB_VIEW_HEAD_VISIBILITY, true)
            dadaToolbar?.visibility = if (hasNavBar)View.VISIBLE else View.GONE
        }
    }

    private fun initView() {
        progressBar = WebViewProgressBar(this)
        progressBar.layoutParams=ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        progressBar.visibility=View.GONE
        initWebView()
        swipeChild.addView(dadaWebView)
        swipeChild.addView(progressBar)
    }

    private fun initWebView() {
dadaWebView= DadaWebView(this)
        dadaWebView?.layoutParams = ViewGroup.LayoutParams(
            MATCH_PARENT,
            MATCH_PARENT
        )

        val jsBridge = JsBridgeImpl()
        jsBridge.delegate=this
        val map = HashMap<String,Any>()
        map["Android"]=jsBridge
        map["DadaApp"]=jsBridge
        dadaWebView?.addJavascriptInterface(jsBridge, "Android")
        dadaWebView?.addJavascriptInterface(jsBridge, "DadaApp")
    }

    override fun gotoVoiceChapter(bookCategoryId: String, courseTitle: String) {
        ToastHelper.toast("点击课件")
    }
}