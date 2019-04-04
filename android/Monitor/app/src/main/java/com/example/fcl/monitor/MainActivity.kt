package com.example.fcl.monitor

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Process
import com.example.fcl.monitor.cpu.ProcessCpuTracker
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    val processCpuTracker = ProcessCpuTracker(Process.myPid())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        testGc.setOnClickListener {
            processCpuTracker.update()
        }
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    external fun stringFromJNI(): String

    companion object {

        // Used to load the 'native-lib' library on application startup.
        init {
            System.loadLibrary("native-lib")
        }
    }
}
