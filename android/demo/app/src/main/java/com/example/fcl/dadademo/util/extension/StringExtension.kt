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

fun String.isPhone(isChina:Boolean):Boolean{
    return if (isChina) {
        val regex="^1\\d{10}$".toRegex()
        regex.matches(this.trim())
    } else{
        this.length>=7
    }
}