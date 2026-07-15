package com.example.streakly

interface KeyValueStorage {
    fun getBool(key: String): Boolean
    fun putBool(key: String, value: Boolean)
}

expect fun createKeyValueStorage(): KeyValueStorage
