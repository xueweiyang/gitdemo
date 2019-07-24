package com.example.andresguard.data

import com.example.andresguard.Log
import com.example.andresguard.util.readIntArray
import com.example.andresguard.util.readIntLE
import com.example.andresguard.util.readShortLE
import java.io.DataInputStream
import java.io.DataOutputStream
import java.nio.ByteBuffer
import java.nio.charset.Charset
import kotlin.experimental.and

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
    val UTF8_DECODER = Charset.forName("UTF-8").newDecoder()

    fun writeTableNameStringBlock(ins:DataInputStream,out:DataOutputStream){
        val type = ins.readShortLE()
        val headersize = ins.readShortLE()
        val chunkSize = ins.readIntLE()
        val stringCount = ins.readIntLE()
        val styleCount = ins.readIntLE()
        val flags = ins.readIntLE()
        val stringsOffset = ins.readIntLE()
        val styleOffset = ins.readIntLE()

        val isUtf8 = flags and UTF8_FLAG != 0
        if(isUtf8){
            Log.i(TAG, "arsc encoding:utf-8")
        } else {
            Log.i(TAG, "arsc encoding:utf-16")
        }

        val stringOffsets = ins.readIntArray(stringCount)
        val strings = IntArray(stringCount)

        var offset = 0
        for (i in 0 until stringCount) {
            stringOffsets[i] = offset

        }
    }

    fun read(ins: DataInputStream): StringBlock {
        val type = ins.readShortLE()
        val headersize = ins.readShortLE()
        val chunkSize = ins.readIntLE()
        val stringCount = ins.readIntLE()
        val styleCount = ins.readIntLE()
        val flags = ins.readIntLE()
        val stringsOffset = ins.readIntLE()
        val styleOffset = ins.readIntLE()

        Log.i(
            TAG,
            "type:$type  headersize:$headersize chunksize:$chunkSize stringcount:$stringCount stylecount:$styleCount " +
                    "flag:$flags stringoffset:$stringsOffset styleoffset:$styleOffset"
        )

        isUtf8 = flags and UTF8_FLAG != 0
        mStringOffsets = ins.readIntArray(stringCount)
        if (styleCount != 0) {
            mStyleOffsets = ins.readIntArray(styleCount)
        }

        //todo 这一块没懂
//        val size = if (styleOffset == 0) chunkSize else styleOffset - stringsOffset
        val size = chunkSize - stringsOffset
//        Log.i(TAG, "string size:$size")
        mStrings = ByteArray(size)
        ins.readFully(mStrings)
        return this
    }

    fun getString(index: Int): String {
        if (index < 0 || mStringOffsets.isEmpty() || index > mStringOffsets.size) {
            return ""
        }
        val offset = mStringOffsets[index]
        val data = getUtf8(mStrings, offset)
        return decodeString(data[0], data[1])
    }

    fun decodeString(offset: Int,length:Int):String{
        val result = UTF8_DECODER.decode(ByteBuffer.wrap(mStrings, offset, length)).toString()
        return result
    }

    fun getUtf8(byteArray: ByteArray, offset: Int): IntArray {
        //第一个字节u16len,第二个字节u8len，后面是内容
        return intArrayOf(offset + 2, byteArray[offset + 1].toInt())
    }

}