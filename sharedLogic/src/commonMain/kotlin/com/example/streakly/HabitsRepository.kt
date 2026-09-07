package com.example.streakly

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

data class Habit(
    val id: Long,
    val name: String,
    val doneToday: Boolean = false,
)

internal class HabitsRepository(private val dao: HabitDao) {

    val habits: Flow<List<Habit>> = dao.observeHabits().map { it.map(HabitEntity::toHabit) }

    suspend fun add(name: String) {
        val trimmed = name.trim()
        if (trimmed.isEmpty()) return
        dao.insert(HabitEntity(name = trimmed))
    }

    suspend fun toggle(id: Long) = dao.toggleDone(id)
}

private fun HabitEntity.toHabit(): Habit =
    Habit(id = id, name = name, doneToday = doneToday)