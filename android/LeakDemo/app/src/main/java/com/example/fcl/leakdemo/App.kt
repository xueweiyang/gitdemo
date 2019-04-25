package com.example.fcl.leakdemo

import android.app.Application
import com.squareup.leakcanary.LeakCanary
import com.tencent.matrix.Matrix
import com.tencent.matrix.iocanary.IOCanaryPlugin
import com.tencent.matrix.iocanary.config.IOConfig.Builder
import com.tencent.matrix.plugin.PluginListener
import com.tencent.matrix.resource.ResourcePlugin
import com.tencent.matrix.resource.config.ResourceConfig
import com.tencent.matrix.trace.TracePlugin
import com.tencent.matrix.trace.config.TraceConfig
import com.tencent.sqlitelint.SQLiteLint.SqlExecutionCallbackMode.CUSTOM_NOTIFY
import com.tencent.sqlitelint.SQLiteLintPlugin
import com.tencent.sqlitelint.config.SQLiteLintConfig

/**
 * Created by galio.fang on 19-1-14
 */
class App : Application() {
    override fun onCreate() {
        super.onCreate()
//        LeakCanary.install(this)
        initMatrix()
    }

    private fun initMatrix() {
        val builder = Matrix.Builder(this)
        builder.patchListener(TestPluginListener(this))
        val dynamicConfig = DynamicConfig()
        val ioCanaryPlugin = IOCanaryPlugin(
            Builder().dynamicConfig(dynamicConfig).build()
        )
        builder.plugin(ioCanaryPlugin)

        val resourcePlugin = ResourcePlugin(
            ResourceConfig.Builder()
                .dynamicConfig(dynamicConfig)
                .setDumpHprof(false)
                .setDetectDebuger(true)
                .build()
        )
        builder.plugin(resourcePlugin)
        ResourcePlugin.activityLeakFixer(this)

        val sqliteLintPlugin = SQLiteLintPlugin(
            SQLiteLintConfig(CUSTOM_NOTIFY)
        )
        builder.plugin(sqliteLintPlugin)

        val traceConfig = TraceConfig.Builder()
            .dynamicConfig(dynamicConfig)
            .enableFPS(true)
            .enableStartUp(true)
            .enableMethodTrace(true)
            .enableStartUp(true)
            .build()
        val tracePlugin = TracePlugin(traceConfig)
        builder.plugin(tracePlugin)

        Matrix.init(builder.build())
        ioCanaryPlugin.start()
        tracePlugin.start()
        resourcePlugin.start()
        sqliteLintPlugin.start()
        Matrix.with().getPluginByClass(TracePlugin::class.java).evilMethodTracer.onCreate()
    }
}
