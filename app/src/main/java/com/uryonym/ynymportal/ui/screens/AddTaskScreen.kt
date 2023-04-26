package com.uryonym.ynymportal.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.uryonym.ynymportal.data.Task

@Composable
fun AddTaskScreen(
    modifier: Modifier = Modifier, taskViewModel: TaskViewModel = viewModel()
) {
    val taskUiState by taskViewModel.taskUiState.collectAsState()
    Box(modifier = modifier) {
        Column() {
            TextField(
                value = taskUiState.taskTitle,
                onValueChange = {},
                label = { Text(text = "タイトル") },
                singleLine = true
            )
            TextField(
                value = taskUiState.taskTitle,
                onValueChange = {},
                label = { Text(text = "詳細") },
                singleLine = true
            )
        }
    }
}
