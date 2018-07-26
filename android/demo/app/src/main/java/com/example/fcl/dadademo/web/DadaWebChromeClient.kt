package com.example.fcl.dadademo.web

import android.webkit.JsResult
import android.webkit.WebChromeClient
import android.webkit.WebView

class DadaWebChromeClient:WebChromeClient() {

    override fun onJsAlert(view: WebView?, url: String?, message: String?, result: JsResult?): Boolean {
        return super.onJsAlert(view, url, message, result)
    }

    override fun onJsConfirm(view: WebView?, url: String?, message: String?, result: JsResult?): Boolean {
        return super.onJsConfirm(view, url, message, result)
    }

}