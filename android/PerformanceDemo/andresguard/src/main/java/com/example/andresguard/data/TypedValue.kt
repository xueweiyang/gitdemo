/*
 * Copyright (C) 2007 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.andresguard.data

import java.util.zip.ZipEntry

/**
 * Container for a dynamically typed data value. Primarily used with
 */
object TypedValue {
    val UNZIP_FILE_PATH = "temp"

    val COMMAND_7ZIP = "7za"
    val COMMAND_ZIPALIGIN = "zipalign"

    val OUT_7ZIP_FILE_PATH = "out_7zip"

    /**
     * 是7zip压缩使用，把制定为不压缩的拷到一起
     */
    val STORED_FILE_PATH = "storedfiles"

    val RES_FILE_PATH = "r"

    val RES_MAPPING_FILE = "resource_mapping_"

    val ZIP_STORED = ZipEntry.STORED

    val ZIP_DEFLATED = ZipEntry.DEFLATED

    val JDK_6 = 6

    val TXT_FILE = ".txt"

    val XML_FILE = ".xml"

    val CONFIG_FILE = "config.xml"

    /**
     * The value contains no data.
     */
    val TYPE_NULL = 0x00

    val TYPE_STRING = 0x03
}
