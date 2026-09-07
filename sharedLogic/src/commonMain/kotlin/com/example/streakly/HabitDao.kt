package com.example.streakly

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
internal interface HabitDao {

    @Query("SELECT * FROM HabitEntity ORDER BY id")
    fun observeHabits(): Flow<List<HabitEntity>>

    @Insert
    suspend fun insert(habit: HabitEntity)

    @Query("UPDATE HabitEntity SET doneToday = NOT doneToday WHERE id = :id")
    suspend fun toggleDone(id: Long)
}