package com.example.taskschedule.util

import android.content.Context
import android.content.SharedPreferences

class SecurityPreferences(context: Context) {

    private val mSharedPreferences: SharedPreferences =
        context.getSharedPreferences("TASK_SCHEDULE", Context.MODE_PRIVATE)

    fun storeString(key: String, value: String) {
        mSharedPreferences.edit().putString(key, value).apply()
    }

    fun getStoredString(key: String): String {
        val value = mSharedPreferences.getString(key, "")
        return if (value.isNullOrEmpty()) "" else value
    }

    fun removeStoredString(key: String) {
        mSharedPreferences.edit().remove(key).apply()
    }
}