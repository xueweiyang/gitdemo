package com.example.andresguard.data

import com.example.andresguard.Log
import com.example.andresguard.util.readIntLE
import com.example.andresguard.util.readShortLE
import com.example.andresguard.util.writeIntLE
import com.example.andresguard.util.writeShortLE
import java.io.DataInputStream
import java.io.DataOutputStream
import java.io.InputStream
import java.io.OutputStream

class Header constructor(
    val type: Short,
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
                val type = inputStream.readShortLE()
                val headerSize = inputStream.readShortLE()
                val size = inputStream.readIntLE()
                Log.i(TAG, "header: type:$type header size:$headerSize size:$size")
                Header(type, size)
            } catch (e: Exception) {
                Log.i(TAG, "header: error")
                Header(TYPE_NONE.toShort(), 0)
            }
        }

        fun write(inputStream: DataInputStream,outputStream: DataOutputStream) :Header{
            return try {
                val type = inputStream.readShortLE()
                val headerSize = inputStream.readShortLE()
                val size = inputStream.readIntLE()
                outputStream.writeShortLE(type)
                outputStream.writeShortLE(headerSize)
                outputStream.writeIntLE(size)
                Header(type,size)
            }  catch (e:Exception) {
                Header(TYPE_NONE.toShort(), 0)
            }
        }
    }

}

