package com.example.fcl.dadademo.util

import com.example.fcl.dadademo.model.Account
import com.example.fcl.dadademo.provider.AccountPreference
import com.example.fcl.kotlindemo.MyApp

object AccountManager : IAccountManager<Account> {

    private val sharedPreference = AccountPreference(MyApp.instance)

    override fun login(account: Account) {
        saveAccount(account)
    }

    fun saveAccount(account: Account, isSendBroadcast: Boolean = false) {
        this.account = account
        sharedPreference.saveUser(account)
    }

    override fun clearAccountInfo() {
    }

    override fun logout() {
    }

    var deviceId = ""
    var accountId = 0
    var token = ""
    var cookie = ""
    var account: Account? = null
        get() {
            if (null == field) {
                field = sharedPreference.userInfo
                accountId = field?.id ?: 0
                token = field?.token ?: ""
                cookie = field?.cookie ?: ""
            }
            return field
        }
    set(value) {
        if (null==value){
            accountId=0
            token=""
            cookie=""
        } else{
            accountId=value.id
            token=value.token
            cookie=value.cookie
        }
        field=value
    }

    fun isLogin(): Boolean {
        return false
    }
}

interface IAccountManager<in T : IAccount> {
    fun login(account: T)
    fun clearAccountInfo()
    fun logout()
}

interface IAccount