package com.example.streakly

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class WatchHandle internal constructor(private val onClose: () -> Unit) {
    fun close() = onClose()
}

fun OnboardingViewModel.watchState(block: (OnboardingState) -> Unit): WatchHandle {
    val watcher = CoroutineScope(Dispatchers.Main)
    watcher.launch { state.collect { block(it) } }
    return WatchHandle { watcher.cancel() }
}

fun OnboardingViewModel.currentState(): OnboardingState = state.value

fun HomeViewModel.watchState(block: (HomeState) -> Unit): WatchHandle {
    val watcher = CoroutineScope(Dispatchers.Main)
    watcher.launch { state.collect { block(it) } }
    return WatchHandle { watcher.cancel() }
}

fun HomeViewModel.currentState(): HomeState = state.value