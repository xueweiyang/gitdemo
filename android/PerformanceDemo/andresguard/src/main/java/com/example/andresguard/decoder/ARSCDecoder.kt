package com.example.andresguard.decoder

import com.android.build.api.dsl.model.TypedValue
import com.example.andresguard.Constant
import com.example.andresguard.Log
import com.example.andresguard.ProguardCache
import com.example.andresguard.data.Header
import com.example.andresguard.data.Header.Companion.TYPE_LIBRARY
import com.example.andresguard.data.Header.Companion.TYPE_SPEC_TYPE
import com.example.andresguard.data.ResPackage
import com.example.andresguard.data.ResType
import com.example.andresguard.data.StringBlock
import com.example.andresguard.data.TypedValue.TYPE_STRING
import com.example.andresguard.util.*
import java.io.*
import kotlin.experimental.and

class ARSCDecoder {

    val TAG = "ARSCDecoder"
    val ENTRY_FLAG_COMPLEX = 0x0001
    val arscPath = "${Constant.PROJECT_PATH}/app/build/outputs/apk/release/app-release-unsigned/resources.arsc"
    val arscOutPath = "${Constant.PROJECT_PATH}/app/build/outputs/apk/release/resources.arsc.bak"
    val mappingFilePath = "${Constant.PROJECT_PATH}/app/build/outputs/apk/release/res_mapping.txt"
    var inputStream: DataInputStream
    var outputStream: DataOutputStream
    lateinit var typeNames: StringBlock
    lateinit var specNames: StringBlock
    lateinit var tableStrings: StringBlock
    lateinit var curHeader: Header
    lateinit var pkg: ResPackage
    var shouldResguardForType = false
    lateinit var type: ResType
    val tableStringResguard = hashMapOf<Int, String>()
    var resId = 0
    var curEntryId = 0
    var mappingWriter:Writer

    init {
        inputStream = DataInputStream(File(arscPath).inputStream())
        outputStream = DataOutputStream(File(arscOutPath).outputStream())
        mappingWriter = BufferedWriter(FileWriter(File(mappingFilePath), false))
    }

    /**
     * 重写arsc文件
     */
    fun write() {
        Log.i(TAG, "--------start write new arsc--------------")
        Header.write(inputStream, outputStream)
        val packageCount = inputStream.readIntLE()
        outputStream.writeIntLE(packageCount)

        StringBlock().writeTableNameStringBlock(inputStream, outputStream, tableStringResguard)
    }

    /**
     * 解析arsc文件
     */
    fun decode() {
        Log.i(TAG, "---------------decode-------------")
        nextChunk()
        val packageCount = readInt()
        Log.i(TAG, "packageCount:$packageCount")
        tableStrings = StringBlock().read(inputStream)
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

        resId = id shl 24
        pkg = ResPackage(id, name)

        pkg.canProguard = pkg.name != "android"

        Log.i(TAG, "reading package id:$id name:$name")

        typeNames = StringBlock().read(inputStream) //解析typestrings
        specNames = StringBlock().read(inputStream) //解析keystrings

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
        inputStream.skip(entryCount * 4L)

        val name = typeNames.getString(id - 1)
        type = ResType(name, pkg)
        shouldResguardForType = isToResguardFile(name)

        while (nextChunk().type.toInt() == Header.TYPE_TYPE) {
            readConfig()
        }
    }

    private fun isToResguardFile(name: String): Boolean {
        return "string" != name && "id" != name && "array" != name
    }

    fun readConfig() {
        inputStream.skip(4)
        val entryCount = inputStream.readIntLE()
        val entryStart = inputStream.readIntLE()
        readConfigFlags()
        val entryoffsets = inputStream.readIntArray(entryCount)
        entryoffsets.forEachIndexed { index, offset ->
            curEntryId = index
            if (offset != -1){
                resId = (resId and 0xffff0000.toInt()) or index
                readEntry()
            }
        }
    }

    fun readEntry() {
        inputStream.skip(2)
        val flags = inputStream.readShortLE()
        val nameId = inputStream.readIntLE()

        Log.e(TAG, "canproguard:${pkg.canProguard}")
        if (pkg.canProguard){
            dealWithNonWhiteList(nameId)
        }

        if ((flags and ENTRY_FLAG_COMPLEX.toShort()).toInt() == 0) {
            readValue(true, nameId)
        } else {
            readComplexEntry(false, nameId)
        }
    }

    private fun dealWithNonWhiteList(nameId: Int) {
        val replaceString = ProguardCache.getName(false)
        generateResIdMapping(pkg.name, type.name, specNames.getString(nameId), replaceString)
        pkg.putSpecReplace(resId, replaceString)
    }

    fun readComplexEntry(flag: Boolean, nameId: Int) {
        val parent = inputStream.readIntLE()
        val count = inputStream.readIntLE()
        for (i in 0 until count) {
            inputStream.readIntLE()
            readValue(flag, nameId)
        }
    }

    fun generateResIdMapping(packageName:String,typeName:String,specName:String,replace:String){
        Log.i(TAG, "generate:$packageName.R.$typeName.$specName $replace")
        mappingWriter.write("    $packageName.R.$typeName.$specName -> $packageName.$typeName.$replace\n")
        mappingWriter.flush()
    }

    var a = false
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
            Log.i(TAG, "raw:$raw")
            if (raw.isEmpty()) {
                return
            }
            val proguard = pkg.getSpecReplace(resId)
            val secondSlash = raw.lastIndexOf("/")
            if (secondSlash==-1){
                return
            }
            var newFilePath = raw.substring(0,secondSlash)
            newFilePath = ProguardCache.getDir(newFilePath) ?: newFilePath
            Log.i(TAG, "proguard:$newFilePath/$proguard")
            tableStringResguard[data] = "$newFilePath/$proguard"
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