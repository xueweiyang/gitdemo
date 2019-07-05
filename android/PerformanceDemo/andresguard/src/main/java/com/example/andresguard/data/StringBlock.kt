package com.example.andresguard.data

import com.android.tools.build.jetifier.core.utils.Log
import com.example.andresguard.util.readIntArray
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
        Header.read(ins)
        val chunkSize = ins.readInt()
        ins.readShort()
        val stringCount = ins.readInt()
        val styleCount = ins.readInt()
        val flags = ins.readInt()
        val stringsOffset = ins.readInt()
        val styleOffset = ins.readInt()

        isUtf8 = flags and UTF8_FLAG != 0
        mStringOffsets = ins.readIntArray(stringCount)
        if (styleCount != 0) {
            mStyleOffsets = ins.readIntArray(styleCount)
        }

        val size = if (styleOffset == 0) chunkSize else styleOffset - stringsOffset
        mStrings = ByteArray(size)
        ins.readFully(mStrings)
        return this
    }

}