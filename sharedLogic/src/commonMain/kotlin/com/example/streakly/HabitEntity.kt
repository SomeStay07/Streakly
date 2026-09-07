package com.example.streakly

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
internal data class HabitEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val doneToday: Boolean = false,
)