package com.example.streakly

import androidx.room.Room
import androidx.room.RoomDatabase

internal actual fun habitDatabaseBuilder(): RoomDatabase.Builder<HabitDatabase> {
    val context = AppContextHolder.appContext
    val dbFile = context.getDatabasePath("streakly.db")
    return Room.databaseBuilder<HabitDatabase>(
        context = context,
        name = dbFile.absolutePath,
    )
}