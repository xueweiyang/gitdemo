package com.example.andresguard.util

import com.android.tools.build.jetifier.core.utils.Log
import java.io.DataInputStream
import java.lang.StringBuilder

fun DataInputStream.readIntArray(length: Int): IntArray {
    val array = IntArray(length)
    for (i in 0 until length) {
        array[i] = readIntLE()
    }
    return array
}

fun DataInputStream.readNullEndedString(length: Int, fixed: Boolean): String {
    val builder = StringBuilder(16)
    var len = length
    while (len-- != 0) {
        val ch = readShortLE()
        if (ch.toInt() == 0) {
            break
        }
        builder.append(ch.toChar())
    }
    //todo enenen
//    if (fixed) {
//        skipBytes(len * 2)
//    }
    return builder.toString()
}

fun DataInputStream.readIntLE(): Int {
    return readByCountLE(4)
}

fun DataInputStream.readShortLE(): Short {
    return readByCountLE(2).toShort()
}

/**
 * 小端模式读取指定数量的字节转换为整数
 * 加上and(0xff)就是无符号整数
 */
private fun DataInputStream.readByCountLE(count: Int): Int {
    val byteArray = ByteArray(count)
    readFully(byteArray, 0, count)
    var result = 0
    byteArray.forEachIndexed { index, byte ->
        val hh = byte.toInt().and(0xff).shl(index * 8)
        result = result.plus(hh)
    }
    return result
}

