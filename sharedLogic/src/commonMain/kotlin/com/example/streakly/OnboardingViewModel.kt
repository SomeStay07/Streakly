package com.example.streakly

class OnboardingViewModel(
    private val repository: OnboardingRepository,
) : MviViewModel<OnboardingState, OnboardingIntent>(
    OnboardingState(completed = repository.isOnboardingCompleted())
) {
    override fun onIntent(intent: OnboardingIntent) = when (intent) {
        OnboardingIntent.StartTapped -> {
            repository.setOnboardingCompleted()
            updateState { it.copy(completed = true) }
        }
    }
}