package com.example.andresguard.data

import com.example.andresguard.Log
import java.io.DataInputStream
import java.io.InputStream

class Header constructor(
    type: Int,
    chunkSize: Int
) {


    companion object {
        val TAG = "Header"
        val TYPE_NONE = -1
        val TYPE_TABLE = 0x0002
        val TYPE_PACKAGE = 0x0200
        val TYPE_TYPE = 0x0201
        val TYPE_SPEC_TYPE = 0x0202
        val TYPE_LIBRARY = 0x0203

        fun read(inputStream: DataInputStream): Header {
            return try {
                val type = inputStream.readInt()
                inputStream.readShort()
                val size = inputStream.readInt()
                Log.i(TAG, "header: type:$type size:$size")
                Header(type, size)
            } catch (e: Exception) {
                Log.i(TAG, "header: error")
                Header(TYPE_NONE, 0)
            }
        }
    }

}

