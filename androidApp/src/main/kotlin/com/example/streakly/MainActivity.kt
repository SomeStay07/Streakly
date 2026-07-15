package com.example.streakly

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent { StreaklyRoot() }
    }
}

@Composable
private fun StreaklyRoot() {
    val viewModel: OnboardingViewModel = viewModel {
        OnboardingViewModel(OnboardingRepository(createKeyValueStorage()))
    }
    val state by viewModel.state.collectAsStateWithLifecycle()

    if (state.completed) {
        HomeScreen()
    } else {
        OnboardingScreen(
            onFinish = { viewModel.onIntent(OnboardingIntent.StartTapped) },
        )
    }
}