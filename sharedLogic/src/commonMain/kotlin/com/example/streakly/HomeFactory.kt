package com.example.streakly

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import kotlin.coroutines.cancellation.CancellationException

class DatabaseUnavailable(message: String) : Exception(message)

private val habitDatabase by lazy { createHabitDatabase() }

fun createHomeViewModel(): HomeViewModel =
    HomeViewModel(HabitsRepository(habitDatabase.habitDao()))

// Открываем базу заранее, на старте приложения: ошибка папки Documents ловится в Swift,
// а не роняет процесс из by lazy, который срабатывает в фабрике createHomeViewModel.
@Throws(DatabaseUnavailable::class, CancellationException::class)
suspend fun openDatabase() {
    withContext(Dispatchers.IO) { habitDatabase }
}
