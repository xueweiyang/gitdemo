package com.example.fcl.dadademo.util

import android.app.Activity
import android.content.Intent
import com.example.fcl.dadademo.base.BaseActivity
import com.example.fcl.dadademo.web.WebBearerActivity
import java.lang.ref.WeakReference

object ActivityManager {

    private var currentActivityWeakRef: WeakReference<Activity>? = null

    var currentActivity: Activity?
        get() = currentActivityWeakRef?.get()
        set(value) {
            currentActivityWeakRef = WeakReference<Activity>(value)
        }

    fun navigateToH5(
        url: String,
        title: String? = null
    ) {
        (currentActivity as? BaseActivity)?.let {
            val intent=Intent(it,WebBearerActivity::class.java)
            intent.putExtra(Constant.WEB_VIEW_URL_EXTRA, url)
            intent.putExtra(Constant.WEB_VIEW_HEAD_EXTRA, title)
            it.startActivity(intent)
        }
    }
}