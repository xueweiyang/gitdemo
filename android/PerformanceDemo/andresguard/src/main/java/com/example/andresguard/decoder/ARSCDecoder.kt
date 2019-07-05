package com.example.andresguard.decoder

import com.example.andresguard.Constant
import com.example.andresguard.Log
import com.example.andresguard.data.Header
import com.example.andresguard.data.StringBlock
import com.example.andresguard.util.readNullEndedString
import java.io.DataInputStream
import java.io.File

object ARSCDecoder {

    val TAG = "StringBlock"
    val arscPath = "${Constant.PROJECT_PATH}/app/build/outputs/apk/release/app-release-unsigned/resources.arsc"
    var inputStream : DataInputStream

    init {
        inputStream = DataInputStream(File(arscPath).inputStream())
    }

    fun decode() {
        Log.i(TAG, "---------------decode-------------")
        nextChunk()
        val packageCount = readInt()
        Log.i(TAG, "packageCount:$packageCount")
        StringBlock.read(inputStream)
        nextChunk()
        for (i in 0 until packageCount){
            readPackage()
        }
    }

    private fun readPackage() {
        val id = readInt()
        val name = inputStream.readNullEndedString(128,true)
//        Log.i(TAG, "reading package name:$name")

    }

    fun nextChunk() {
        Header.read(inputStream)
    }

    fun readInt() = inputStream.readInt()
    fun readShort() = inputStream.readShort()

}