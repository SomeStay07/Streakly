package com.example.streakly

class OnboardingRepository(
    private val storage: KeyValueStorage,
) {
    fun isOnboardingCompleted(): Boolean =
        storage.getBool(KEY_ONBOARDING_COMPLETED)

    fun setOnboardingCompleted() {
        storage.putBool(KEY_ONBOARDING_COMPLETED, true)
    }

    private companion object {
        const val KEY_ONBOARDING_COMPLETED = "onboarding_completed"
    }
}