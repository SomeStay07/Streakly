package com.example.streakly

data class OnboardingState(
    val completed: Boolean = false,
)

sealed interface OnboardingIntent {
    data object StartTapped : OnboardingIntent
}