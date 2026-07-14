package com.example.streakly

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        val storage = createKeyValueStorage()
        setContent {
            var onboardingDone by remember {
                mutableStateOf(storage.getBool(KEY_ONBOARDING_COMPLETED))
            }
            if (onboardingDone) {
                HomeScreen()
            } else {
                OnboardingScreen(onFinish = {
                    storage.putBool(KEY_ONBOARDING_COMPLETED, true)
                    onboardingDone = true
                })
            }
        }
    }
}
