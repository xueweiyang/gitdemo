package com.example.fcl.leakdemo

import android.content.Context
import android.util.Log
import com.tencent.matrix.plugin.DefaultPluginListener
import com.tencent.matrix.report.Issue

/**
 * Created by galio.fang on 19-4-24
 */
class TestPluginListener(context: Context) : DefaultPluginListener(context) {
    override fun onReportIssue(issue: Issue) {
        super.onReportIssue(issue)
        Log.d("TestPluginListener", issue.toString())
    }
}
