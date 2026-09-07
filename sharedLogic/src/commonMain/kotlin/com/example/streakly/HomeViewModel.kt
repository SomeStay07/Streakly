package com.example.streakly

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

data class HomeState(
    val habits: List<Habit> = emptyList(),
)

sealed interface HomeIntent {
    data class AddTapped(val name: String) : HomeIntent
    data class HabitTapped(val id: Long) : HomeIntent
}

class HomeViewModel internal constructor(
    private val repository: HabitsRepository,
) : MviViewModel<HomeState, HomeIntent>(HomeState()) {

    init {
        viewModelScope.launch {
            repository.habits.collect { list ->
                updateState { it.copy(habits = list) }
            }
        }
    }

    override fun onIntent(intent: HomeIntent) {
        when (intent) {
            is HomeIntent.AddTapped -> viewModelScope.launch { repository.add(intent.name) }
            is HomeIntent.HabitTapped -> viewModelScope.launch { repository.toggle(intent.id) }
        }
    }
}