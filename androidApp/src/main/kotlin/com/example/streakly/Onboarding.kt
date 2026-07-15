package com.example.streakly

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun OnboardingScreen(onFinish: () -> Unit) {
    val pagerState = rememberPagerState(pageCount = { 3 })
    Column {
        HorizontalPager(state = pagerState, modifier = Modifier.weight(1f)) { page ->
            when (page) {
                0 -> OnboardingPage("Streakly", "Трекер привычек, который держит твой streak")
                1 -> OnboardingPage("Серии и heatmap", "Отмечай привычки, расти streak")
                else -> OnboardingPage("Поехали", "Заводим первую привычку", onFinish)
            }
        }
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            repeat(3) { i ->
                val active = pagerState.currentPage == i
                Box(
                    Modifier
                        .padding(4.dp)
                        .size(if (active) 10.dp else 8.dp)
                        .background(if (active) Color.Black else Color.Gray, CircleShape)
                )
            }
        }
    }
}

@Composable
fun OnboardingPage(title: String, text: String, onFinish: (() -> Unit)? = null) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(Modifier.weight(1f))
        Box(Modifier
            .size(80.dp)
            .background(Color(0xFF66D9E8), CircleShape))
        Spacer(Modifier.height(16.dp))
        Text(title, style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(8.dp))
        Text(text, color = Color.Gray)
        Spacer(Modifier.weight(1f))
        if (onFinish != null) {
            Button(onClick = onFinish) { Text("Начать") }
            Spacer(Modifier.height(24.dp))
        }
    }
}
