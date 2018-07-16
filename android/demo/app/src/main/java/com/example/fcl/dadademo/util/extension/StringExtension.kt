package com.example.fcl.dadademo.util.extension

import com.example.fcl.dadademo.api.Constant

fun String.realImageUrl() : String {
    return if (startsWith("http")) {
        this
    } else{
        val pattern = "%s%s"
        pattern.format(Constant.RESOURCE_DOMAIN, this)
    }
}