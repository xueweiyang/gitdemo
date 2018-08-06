package com.example.fcl.kotlindemo.test

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.fcl.kotlindemo.R
import kotlinx.android.synthetic.main.activity_web.webview

class WebActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web)

        val settings=webview.settings

        settings.displayZoomControls=true
        settings.javaScriptEnabled=true

        webview.webViewClient = MyWebClient()
        val url = intent.getStringExtra("extra_url")
        Log.e("WebActivity", url)
        webview.loadUrl(url)
    }
}

class MyWebClient : WebViewClient(){
    override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
        view?.loadUrl(url)
        return true
    }
}