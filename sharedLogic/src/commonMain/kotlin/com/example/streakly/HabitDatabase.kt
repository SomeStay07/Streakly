package com.example.streakly

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

@Database(entities = [HabitEntity::class], version = 1)
@ConstructedBy(HabitDatabaseConstructor::class)
internal abstract class HabitDatabase : RoomDatabase() {
    abstract fun habitDao(): HabitDao
}

@Suppress("KotlinNoActualForExpect")
internal expect object HabitDatabaseConstructor : RoomDatabaseConstructor<HabitDatabase> {
    override fun initialize(): HabitDatabase
}

internal expect fun habitDatabaseBuilder(): RoomDatabase.Builder<HabitDatabase>

internal fun createHabitDatabase(): HabitDatabase =
    habitDatabaseBuilder()
        .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
        .build()