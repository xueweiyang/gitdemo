package com.example.andresguard.data

class ResPackage constructor(
    val id:Int,
    val name: String
) {

    var canProguard = false
    val specNameReplace = hashMapOf<Int,String>()
    val specNameBlock = hashSetOf<String>()

    fun getSpecReplace(resid:Int) : String? {
        return specNameReplace[resid]
    }

    fun putSpecReplace(resid: Int,value:String){
        specNameReplace.put(resid, value)
    }
}