package com.example.andresguard.util

import java.io.File
import java.io.InputStream
import java.io.OutputStream
import java.nio.charset.Charset
import java.util.zip.ZipFile

infix fun File.unZipTo(path: String) {
    ZipFile(this, Charset.forName("GBK")).unZipTo(path)
}

infix fun ZipFile.unZipTo(path: String) {
    for (entry in entries()) {
        if (entry.isDirectory) {
            File("$path/${entry.name}").mkdirs()
        } else {
            val input = getInputStream(entry)
            val outputFile = File("$path/${entry.name}")
            if (!outputFile.exists()) {
                outputFile.smartCreateNewFile()
            }
            val output = outputFile.outputStream()
            input.writeTo(output)
        }
    }
}

fun InputStream.writeTo(
    outputStream: OutputStream, bufferSize: Int = 1024 * 2,
    closeInput: Boolean = true, closeOutput: Boolean = true
) {
    val buffer = ByteArray(bufferSize)
    val br = this.buffered()
    val bw = outputStream.buffered()
    var length = 0
    while ({ length = br.read(buffer);length != -1 }()) {
        bw.write(buffer, 0, length)
    }
    bw.flush()
    if (closeInput) {
        close()
    }
    if (closeOutput) {
        outputStream.close()
    }
}

fun File.smartCreateNewFile(): Boolean {
    if (exists()) return true
    if (parentFile.exists()) {
        return createNewFile()
    }
    if (parentFile.mkdirs()) {
        if (this.createNewFile()) {
            return true
        }
    }
    return false
}