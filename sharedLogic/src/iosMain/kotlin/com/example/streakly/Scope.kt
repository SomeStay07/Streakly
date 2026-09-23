package com.example.streakly

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.cancel

// На Android viewModelScope закрывает clear() у ViewModel, ещё до onCleared. На iOS звать некому:
// clear() у ViewModel не публичный и в заголовок не попадает, поэтому отмену даёт общий код.
fun MviViewModel<*, *>.destroy() = viewModelScope.cancel()
