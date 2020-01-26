package com.example.motivationgenerator.util

import android.content.Context
import android.content.SharedPreferences

class SecurityPreferences(context: Context) {
    private val mSharedPreferences: SharedPreferences =
        context.getSharedPreferences("motivation", Context.MODE_PRIVATE)

    fun storeString(key: String, value: String) {
        mSharedPreferences.edit().putString(key, value).apply()
    }

    fun getStoredString(key: String): String {
        val value = mSharedPreferences.getString(key, "")
        return if (value.isNullOrEmpty()) "" else value
    }
}