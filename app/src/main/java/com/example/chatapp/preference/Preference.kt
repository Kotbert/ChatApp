package com.example.chatapp.preference

import android.content.Context
import com.example.chatapp.firebase.data_base_utils.UserTable

class Preference(context: Context) {
    private val pref =
        context.getSharedPreferences(UserTable.CHAT_PREFERENCE_USER, Context.MODE_PRIVATE)
    private val editor = pref.edit()

    fun saveBoolean(key: String, value: Boolean) = editor.putBoolean(key, value).apply()
    fun getBoolean(key: String) = pref.getBoolean(key, false)
    fun saveString(key: String, value: String) = editor.putString(key, value).apply()
    fun getString(key: String) = pref.getString(key, null)
    fun clearPreference() = editor.clear().apply()

    companion object{
        const val TOKEN_KEY = "user_token"
    }
}