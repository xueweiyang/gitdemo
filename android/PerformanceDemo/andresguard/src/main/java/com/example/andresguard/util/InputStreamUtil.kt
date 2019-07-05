package com.example.andresguard.util

import java.io.DataInputStream
import java.lang.StringBuilder

fun DataInputStream.readIntArray(length: Int): IntArray {
    val array = IntArray(length)
    for (i in 0 until length) {
        array[i] = readInt()
    }
    return array
}

fun DataInputStream.readNullEndedString(length: Int,fixed:Boolean):String{
    val builder=StringBuilder(16)
    var len = length
    while (len-- != 0) {
        val ch = readChar()
        if (ch.equals(0)) {
            break
        }
        builder.append(ch.toChar())
    }
    if (fixed){
        skipBytes(len*2)
    }
    return builder.toString()
}