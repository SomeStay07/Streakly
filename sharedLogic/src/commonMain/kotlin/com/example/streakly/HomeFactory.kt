package com.example.streakly

private val habitDatabase by lazy { createHabitDatabase() }

fun createHomeViewModel(): HomeViewModel =
    HomeViewModel(HabitsRepository(habitDatabase.habitDao()))