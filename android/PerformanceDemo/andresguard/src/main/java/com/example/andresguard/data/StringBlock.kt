package com.example.andresguard.data

import com.example.andresguard.Log
import com.example.andresguard.util.*
import java.io.DataInputStream
import java.io.DataOutputStream
import java.nio.ByteBuffer
import java.nio.charset.Charset
import kotlin.experimental.and

class StringBlock {

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

    fun writeTableNameStringBlock(
        ins: DataInputStream, out: DataOutputStream,
        tableProguradMap: Map<Int, String>
    ) {
        val type = ins.readShortLE()
        val headersize = ins.readShortLE()
        val chunkSize = ins.readIntLE()
        val stringCount = ins.readIntLE()
        val styleCount = ins.readIntLE()
        val flags = ins.readIntLE()
        val stringsOffset = ins.readIntLE()
        val styleOffset = ins.readIntLE()

        val isUtf8 = flags and UTF8_FLAG != 0
        if (isUtf8) {
            Log.i(TAG, "arsc encoding:utf-8")
        } else {
            Log.i(TAG, "arsc encoding:utf-16")
        }

        val stringOffsets = ins.readIntArray(stringCount)

        val size = chunkSize - stringsOffset
        val strings = ByteArray(size)
        ins.readFully(strings)

        var totalSize = 0
        out.writeShortLE(type)
        out.writeShortLE(headersize)
        totalSize += 4

        totalSize += 6 * 4 + 4 * stringCount
        val mStrings = ByteArray(strings.size)
        val mStringOffsets = IntArray(stringCount)
        System.arraycopy(stringOffsets, 0, mStringOffsets, 0, mStringOffsets.size)

        var offset = 0
        for (i in 0 until stringCount) {
            mStringOffsets[i] = offset
            if (tableProguradMap[i] == null) {

            } else {
                val name = tableProguradMap[i] ?: continue
                Log.i(TAG, "table index:$i name:$name")
                if (isUtf8) {
                    mStrings[offset++] = name.length.toByte()
                    mStrings[offset++] = name.length.toByte()
                    totalSize+=2
                    val tempByte = name.toByteArray()
                    System.arraycopy(tempByte,0,mStrings,offset,tempByte.size)
                    offset+=name.length
                    mStrings[offset++]=NULL
                    totalSize+=name.length+1
                }
            }
        }

        val mSize = totalSize - stringsOffset
        if (mSize%4 != 0) {
            val add = 4 - mSize%4
            for (i in 0 until add) {
                mStrings[offset++]=NULL
                totalSize++
            }
        }

        out.writeIntLE(totalSize)
        out.writeIntLE(stringCount)
        out.writeIntLE(styleCount)
        out.writeIntLE(flags)
        out.writeIntLE(stringsOffset)
        out.writeIntLE(styleOffset)
        out.writeIntArray(mStringOffsets)
        out.write(mStrings, 0, offset)
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

    fun decodeString(offset: Int, length: Int): String {
        val result = UTF8_DECODER.decode(ByteBuffer.wrap(mStrings, offset, length)).toString()
        return result
    }

    fun getUtf8(byteArray: ByteArray, offset: Int): IntArray {
        //第一个字节u16len,第二个字节u8len，后面是内容
        return intArrayOf(offset + 2, byteArray[offset + 1].toInt())
    }

}