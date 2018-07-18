package com.example.fcl.dadademo.provider

import android.content.Context
import android.text.TextUtils
import com.example.fcl.dadademo.model.Account
import com.example.fcl.dadademo.util.BasePrefrerenceRepository
import com.google.gson.Gson

class AccountPreference(context: Context) : BasePrefrerenceRepository(context) {

    override val name="dada_android"

    fun saveUser(userInfo:Account) {
        val jsonStr=Gson().toJson(userInfo)
        setString("key_user_info", jsonStr)
    }

    val userInfo:Account?
    get() {
        val jsonStr=getString("key_user_info")
        return if (TextUtils.isEmpty(jsonStr)) null else Gson().fromJson(jsonStr, Account::class.java)
    }
}