package com.example.andresguard.data

class ResPackage constructor(
    val id:Int,
    val name: String
) {

    var canProguard = false
    val specNameReplace = mapOf<Int,String>()
    val specNameBlock = hashSetOf<String>()

    fun getSpecReplace(resid:Int) : String? {
        return specNameReplace[resid]
    }
}