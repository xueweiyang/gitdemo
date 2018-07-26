package com.example.fcl.dadademo.web

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.View
import android.webkit.WebSettings
import android.webkit.WebView

class DadaWebView @JvmOverloads constructor(
    context:Context,
    attrs:AttributeSet?=null
) : WebView(context, attrs) {

    private var webClient:DadaWebViewClient=DadaWebViewClient()
    private var webChromeClient:DadaWebChromeClient= DadaWebChromeClient()

    init {
        settings.javaScriptEnabled=true
        settings.pluginState = WebSettings.PluginState.ON
        settings.useWideViewPort = true
        settings.allowContentAccess = true
        settings.loadWithOverviewMode = true
        settings.mediaPlaybackRequiresUserGesture = false
        settings.useWideViewPort = true
        settings.loadWithOverviewMode = true
        settings.domStorageEnabled = true
        settings.cacheMode = WebSettings.LOAD_DEFAULT
        settings.builtInZoomControls = true
        settings.setSupportZoom(true)
        settings.setAppCachePath(context.getDir("cache", Context.MODE_PRIVATE).path)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true)
        }
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        this.isFocusable = true
        this.isFocusableInTouchMode = true
        webViewClient = webClient
        setWebChromeClient(webChromeClient)
    }

//    fun addJavascriptInterface(jsBridges:HashMap<String,Any>) {
//        for (entry in jsBridges) {
//            addJavascriptInterface(entry.value, entry.key)
//        }
//    }

}