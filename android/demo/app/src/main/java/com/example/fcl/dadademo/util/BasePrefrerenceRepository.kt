package com.example.fcl.dadademo.util

import android.content.Context
import android.content.SharedPreferences
import kotlin.LazyThreadSafetyMode.SYNCHRONIZED

abstract class BasePrefrerenceRepository(context: Context) {

    protected val sharedPreferences : SharedPreferences by lazy(mode = SYNCHRONIZED) {
        context.getSharedPreferences(name, Context.MODE_PRIVATE)
    }

    protected abstract val name:String
    fun clearData() {
        sharedPreferences.edit().clear().apply()
    }

    fun setBoolean(keyName: String, value: Boolean) {
        val editor = sharedPreferences.edit()
        editor.putBoolean(keyName, value)
        editor.apply()
    }

    fun getBoolean(keyName: String, defValue: Boolean = false): Boolean {
        val sp = sharedPreferences
        return sp.getBoolean(keyName, defValue)
    }

    fun setString(keyName: String, value: String) {
        val editor = sharedPreferences.edit()
        editor.putString(keyName, value)
        editor.apply()
    }

    fun getString(keyName: String): String {
        val sp = sharedPreferences
        return sp.getString(keyName, "")
    }

    fun setInt(keyName: String, value: Int) {
        val editor = sharedPreferences.edit()
        editor.putInt(keyName, value)
        editor.apply()
    }

    fun getInt(keyName: String, defValue: Int = 0): Int {
        val sp = sharedPreferences
        return sp.getInt(keyName, defValue)
    }
}