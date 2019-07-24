package com.example.andresguard.decoder

import com.android.build.api.dsl.model.TypedValue
import com.example.andresguard.Constant
import com.example.andresguard.Log
import com.example.andresguard.data.Header
import com.example.andresguard.data.Header.Companion.TYPE_LIBRARY
import com.example.andresguard.data.Header.Companion.TYPE_SPEC_TYPE
import com.example.andresguard.data.ResPackage
import com.example.andresguard.data.ResType
import com.example.andresguard.data.StringBlock
import com.example.andresguard.data.TypedValue.TYPE_STRING
import com.example.andresguard.util.*
import java.io.DataInputStream
import java.io.DataOutputStream
import java.io.File
import kotlin.experimental.and

class ARSCDecoder {

    val TAG = "ARSCDecoder"
    val ENTRY_FLAG_COMPLEX = 0x0001
    val arscPath = "${Constant.PROJECT_PATH}/app/build/outputs/apk/release/app-release-unsigned/resources.arsc"
    val arscOutPath = "${Constant.PROJECT_PATH}/app/build/outputs/apk/release/resources.arsc.bak"
    var inputStream: DataInputStream
    var outputStream: DataOutputStream
    lateinit var typeNames: StringBlock
    lateinit var tableStrings: StringBlock
    lateinit var curHeader: Header
    lateinit var pkg:ResPackage
    var shouldResguardForType=false
    lateinit var type:ResType
    val tableStringResguard= hashMapOf<Int,String>()

    init {
        inputStream = DataInputStream(File(arscPath).inputStream())
        outputStream = DataOutputStream(File(arscOutPath).outputStream())
    }

    /**
     * 重写arsc文件
     */
    fun write() {
        Log.i(TAG, "--------start write new arsc--------------")
        Header.write(inputStream, outputStream)
        val packageCount = inputStream.readIntLE()
        outputStream.writeIntLE(packageCount)
    }

    /**
     * 解析arsc文件
     */
    fun decode() {
        Log.i(TAG, "---------------decode-------------")
        nextChunk()
        val packageCount = readInt()
        Log.i(TAG, "packageCount:$packageCount")
        tableStrings = StringBlock.read(inputStream)
        nextChunk()
        for (i in 0 until packageCount) {
            readPackage()
        }
    }

    private fun readPackage(): ResPackage {
        val id = readInt()
        val name = inputStream.readNullEndedString(128, true)


        val typeStrings = inputStream.readIntLE()
        val lastPublicType = inputStream.readIntLE()
        val keyStrings = inputStream.readIntLE()
        val lastPublicKey = inputStream.readIntLE()
        Log.i(TAG, "typeString:$typeStrings lastType:$lastPublicType keystring:$keyStrings lastKey:$lastPublicKey")

        //todo 不知道为什么跳过4个字节，什么都没有
        inputStream.skip(4)

        pkg = ResPackage(id, name)

        pkg.canProguard = pkg.name != "android"

        Log.i(TAG, "reading package id:$id name:$name")

        typeNames = StringBlock.read(inputStream) //解析typestrings
        StringBlock.read(inputStream) //解析keystrings

        while (TYPE_SPEC_TYPE == nextChunk().type.toInt()) {
            readTableTypeSpec()
        }

        return pkg
    }

    private fun readTableTypeSpec() {
        val id = inputStream.readByte()
        inputStream.skip(3)
        val entryCount = inputStream.readIntLE()
//        Log.e(TAG, "entrycount:$entryCount")
        typeNames.getString(id - 1)
        inputStream.skip(entryCount * 4L)

        val name = typeNames.getString(id - 1)
        type = ResType(name, pkg)
        shouldResguardForType = isToResguardFile(name)

        while (nextChunk().type.toInt() == Header.TYPE_TYPE) {
            readConfig()
        }
    }

    private fun isToResguardFile(name:String):Boolean{
        return "string" != name && "id" != name && "array" != name
    }

    fun readConfig() {
        inputStream.skip(4)
        val entryCount = inputStream.readIntLE()
        val entryStart = inputStream.readIntLE()
        readConfigFlags()
        val entryoffsets = inputStream.readIntArray(entryCount)
        entryoffsets.forEachIndexed { index, offset ->
            readEntry()
        }
    }

    fun readEntry() {
        inputStream.skip(2)
        val flags = inputStream.readShortLE()
        val nameId = inputStream.readIntLE()
        if ((flags and ENTRY_FLAG_COMPLEX.toShort()).toInt() == 0) {
            readValue(true, nameId)
        } else {
            readComplexEntry(false, nameId)
        }
    }

    fun readComplexEntry(flag: Boolean, nameId: Int) {
        val parent = inputStream.readIntLE()
        val count = inputStream.readIntLE()
        for (i in 0 until count) {
            inputStream.readIntLE()
            readValue(flag, nameId)
        }
    }
var a=false
    fun readValue(flag: Boolean, nameId: Int) {
        inputStream.skip(2)
        inputStream.skip(1)
        val type = inputStream.readByte()
        val data = inputStream.readIntLE()

//        if (pkg.canProguard && flag
//            && type.toInt() == TYPE_STRING
//            && shouldResguardForType) {

            if (tableStringResguard[data] == null) {
                val raw = tableStrings.getString(data)
//                if (!a){
                    a=true
                    Log.e(TAG, "table data:$raw")
//                }
            }

//        }
    }

    fun readConfigFlags() {
        val size = inputStream.readIntLE()
//        val mcc = inputStream.readShortLE()
//        val mnc = inputStream.readShortLE()
//
//        val language = charArrayOf(inputStream.readByte().toChar(), inputStream.readByte().toChar())
//        val conutry = charArrayOf(inputStream.readByte().toChar(), inputStream.readByte().toChar())
//
//        val orientation = inputStream.readByte()
//        val touchscreen = inputStream.readByte()
        inputStream.skip(size.toLong())
    }

    fun nextChunk(): Header {
        curHeader = Header.read(inputStream)
        return curHeader
    }

    fun readInt() = inputStream.readIntLE()
    fun readShort() = inputStream.readShortLE()

}