package com.example.andresguard.data

import com.android.tools.build.jetifier.core.utils.Log
import com.example.andresguard.util.readIntArray
import com.example.andresguard.util.readIntLE
import com.example.andresguard.util.readShortLE
import java.io.DataInputStream

object StringBlock {

    val TAG = "StringBlock"
    private val CHUNK_STRINGPOOL_TYPE = 0x001C0001
    private val UTF8_FLAG = 0x00000100
    private val CHUNK_NULL_TYPE = 0x00000000
    private val NULL: Byte = 0

    var mStringOffsets = intArrayOf()
    var mStrings = byteArrayOf()
    var mStyleOffsets = intArrayOf()
    var mStyles = intArrayOf()
    var isUtf8 = false
    var mStringOwns = intArrayOf()

    fun read(ins: DataInputStream): StringBlock {
        ins.readShortLE()
        ins.readShortLE()
        val chunkSize = ins.readIntLE()
        val stringCount = ins.readIntLE()
        val styleCount = ins.readIntLE()
        val flags = ins.readIntLE()
        val stringsOffset = ins.readIntLE()
        val styleOffset = ins.readIntLE()

        Log.i(TAG, "chunksize:$chunkSize stringcount:$stringCount stylecount:$styleCount " +
                "flag:$flags stringoffset:$stringsOffset styleoffset:$styleOffset")

        isUtf8 = flags and UTF8_FLAG != 0
        mStringOffsets = ins.readIntArray(stringCount)
        Log.i(TAG, "string0:${mStringOffsets[0]} 2:${mStringOffsets[2]} last:${mStringOffsets[stringCount-1]}")
        if (styleCount != 0) {
            mStyleOffsets = ins.readIntArray(styleCount)
        }

        //todo 这一块没懂
//        val size = if (styleOffset == 0) chunkSize else styleOffset - stringsOffset
        val size = chunkSize - stringsOffset
        Log.i(TAG, "string size:$size")
        mStrings = ByteArray(size)
        ins.readFully(mStrings)
        return this
    }

}