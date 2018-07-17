package com.example.fcl.dadademo.util

import com.example.fcl.dadademo.model.Account

object AccountManager{

    var deviceId=""
    var account:Account?=null


    fun isLogin() : Boolean{
        return false
    }

}

interface IAccountManager<in T : IAccount> {
    fun login(account: T)
    fun clearAccountInfo()
    fun logout()
}

interface IAccount