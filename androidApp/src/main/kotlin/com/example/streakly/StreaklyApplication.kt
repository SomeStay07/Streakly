package com.example.streakly

import android.app.Application

class StreaklyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        AppContextHolder.appContext = applicationContext
    }
}
