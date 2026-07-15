package com.example.streakly

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class Habit(
    val id: Long,
    val name: String,
    val doneToday: Boolean = false,
)

class HabitsRepository {

    private val _habits = MutableStateFlow(emptyList<Habit>())
    val habits: StateFlow<List<Habit>> = _habits.asStateFlow()

    private var nextId = 1L

    fun add(name: String) {
        val trimmed = name.trim()
        if (trimmed.isEmpty()) return
        val habit = Habit(id = nextId++, name = trimmed)
        _habits.update { it + habit }
    }

    fun toggle(id: Long) {
        _habits.update { list ->
            list.map { habit ->
                if (habit.id == id) habit.copy(doneToday = !habit.doneToday) else habit
            }
        }
    }
}