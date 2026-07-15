package com.example.streakly

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    val viewModel: HomeViewModel = viewModel {
        HomeViewModel(HabitsRepository())
    }
    val state by viewModel.state.collectAsStateWithLifecycle()

    var draft by remember { mutableStateOf("") }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Streakly") }) },
    ) { innerPadding ->
        Column(
            Modifier
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
        ) {
            Row {
                OutlinedTextField(
                    value = draft,
                    onValueChange = { draft = it },
                    modifier = Modifier.weight(1f),
                    placeholder = { Text("Новая привычка") },
                )
                Button(
                    onClick = {
                        viewModel.onIntent(HomeIntent.AddTapped(draft))
                        draft = ""
                    },
                    modifier = Modifier.padding(start = 8.dp),
                ) { Text("+") }
            }

            LazyColumn {
                items(state.habits, key = { it.id }) { habit ->
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .clickable { viewModel.onIntent(HomeIntent.HabitTapped(habit.id)) }
                            .padding(vertical = 8.dp),
                    ) {
                        Checkbox(checked = habit.doneToday, onCheckedChange = null)
                        Text(habit.name, Modifier.padding(start = 8.dp))
                    }
                }
            }
        }
    }
}