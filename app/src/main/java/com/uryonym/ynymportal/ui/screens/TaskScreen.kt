package com.uryonym.ynymportal.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.uryonym.ynymportal.data.Task

@Composable
fun TaskScreen(
    taskViewModel: TaskViewModel = viewModel(), modifier: Modifier = Modifier
) {
    val taskUiState by taskViewModel.taskUiState.collectAsState()
    Box() {
        TaskList(taskUiState.taskList)
    }
}

@Composable
fun TaskList(
    taskList: List<Task>, modifier: Modifier = Modifier
) {
    LazyColumn {
        items(taskList) { task ->
            TaskItem(task)
        }
    }
}

@Composable
fun TaskItem(
    task: Task, modifier: Modifier = Modifier
) {
    Column {
        ListItem(headlineText = { Text(text = task.title) })
        Divider()
    }
}
