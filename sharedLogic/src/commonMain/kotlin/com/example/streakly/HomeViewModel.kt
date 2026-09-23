package com.example.streakly

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

data class HomeState(
    val habits: List<Habit> = emptyList(),
)

sealed interface HomeIntent {
    data class AddTapped(val name: String) : HomeIntent
    data class HabitTapped(val id: Long) : HomeIntent
}

sealed interface HomeEffect {
    data object EmptyName : HomeEffect
}

class HomeViewModel internal constructor(
    private val repository: HabitsRepository,
) : MviViewModel<HomeState, HomeIntent>(HomeState()) {

    private val _effects = Channel<HomeEffect>()
    val effects: Flow<HomeEffect> = _effects.receiveAsFlow()

    init {
        viewModelScope.launch {
            repository.habits.collect { list ->
                updateState { it.copy(habits = list) }
            }
        }
    }

    override fun onIntent(intent: HomeIntent) {
        when (intent) {
            is HomeIntent.AddTapped -> viewModelScope.launch {
                if (!repository.add(intent.name)) _effects.send(HomeEffect.EmptyName)
            }
            is HomeIntent.HabitTapped -> viewModelScope.launch { repository.toggle(intent.id) }
        }
    }
}
