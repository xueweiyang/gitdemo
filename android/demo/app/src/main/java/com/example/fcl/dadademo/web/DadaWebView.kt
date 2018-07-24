package com.example.fcl.dadademo.web

import android.content.Context
import android.util.AttributeSet
import android.webkit.WebView

class DadaWebView @JvmOverloads constructor(
    context:Context,
    attrs:AttributeSet?=null
) : WebView(context, attrs) {

    private var webClient:DadaWebViewClient=DadaWebViewClient()

    init {
        settings.javaScriptEnabled=true
        webViewClient = webClient
    }

    fun addJavascriptInterface(jsBridges:HashMap<String,Any>) {
        for (entry in jsBridges) {
            addJavascriptInterface(entry.value, entry.key)
        }
    }

}