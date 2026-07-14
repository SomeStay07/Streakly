package com.example.streakly

import platform.Foundation.NSUserDefaults

private class IosKeyValueStorage : KeyValueStorage {
    private val defaults = NSUserDefaults.standardUserDefaults

    override fun getBool(key: String): Boolean = defaults.boolForKey(key)

    override fun putBool(key: String, value: Boolean) =
        defaults.setBool(value, forKey = key)
}

actual fun createKeyValueStorage(): KeyValueStorage = IosKeyValueStorage()
