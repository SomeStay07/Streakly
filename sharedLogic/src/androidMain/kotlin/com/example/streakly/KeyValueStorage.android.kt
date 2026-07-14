package com.example.streakly

import android.content.Context

object AppContextHolder {
    lateinit var appContext: Context
}

private class AndroidKeyValueStorage(context: Context) : KeyValueStorage {
    private val prefs = context.getSharedPreferences("streakly_prefs", Context.MODE_PRIVATE)

    override fun getBool(key: String): Boolean = prefs.getBoolean(key, false)

    override fun putBool(key: String, value: Boolean) {
        prefs.edit().putBoolean(key, value).apply()
    }
}

actual fun createKeyValueStorage(): KeyValueStorage =
    AndroidKeyValueStorage(AppContextHolder.appContext)
