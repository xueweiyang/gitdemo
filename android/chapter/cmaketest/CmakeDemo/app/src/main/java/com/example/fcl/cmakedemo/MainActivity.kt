package com.example.fcl.cmakedemo

import android.Manifest
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.support.v4.app.ActivityCompat
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import java.io.FileOutputStream
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE),0)
        // Example of a call to a native method
        common.setOnClickListener {
            writeToFile()
        }
        c.setOnClickListener {
            cFile()
        }
    }

    private fun cFile() {
        val startTime = System.currentTimeMillis()
        val result = openMem(Environment.getExternalStorageDirectory().absolutePath + "/1/common.txt", 120)
        val endTime=System.currentTimeMillis()
        Log.e(TAG, "c write file time:${endTime-startTime} result:${result}")
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

    }

    private fun writeToFile() {
        val startTime = System.currentTimeMillis()
        try {
            val file = File(Environment.getExternalStorageDirectory().absolutePath + "/1/common.txt")
            Log.e(TAG, "file path:${file.absolutePath}")
            file.deleteOnExit()
            val data = ByteArray(512) { 'a'.toByte() }
            FileOutputStream(file).use {
                for (i in 0..80000) {
                    it.write(data)
                }
                it.flush()
                val endTime = System.currentTimeMillis()
                Log.e(TAG, "write file time:${endTime-startTime}")
            }
        } catch (e: Exception) {
            Log.e(TAG, e.message)
        }

    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    external fun stringFromJNI(): String
    external fun openMem(name:String,length:Int): Int

    companion object {
        const val TAG="MainActivity"
        // Used to load the 'native-lib' library on application startup.
        init {
            System.loadLibrary("native-lib")
            System.loadLibrary("JNI_Shm")        }
    }
}
