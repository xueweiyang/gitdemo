package com.example.fcl.dadademo.web

import android.webkit.JavascriptInterface

class JsBridgeImpl : JsBridge{

    var delegate:JsBridgeDelegate?=null

    @JavascriptInterface
    override fun gotoVoiceChapter(bookCategoryId: String, courseTitle: String) {
        delegate?.gotoVoiceChapter(bookCategoryId, courseTitle)
    }
}