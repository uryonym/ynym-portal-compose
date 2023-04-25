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
    modifier: Modifier = Modifier, taskViewModel: TaskViewModel = viewModel()
) {
    val taskUiState by taskViewModel.taskUiState.collectAsState()
    Box(modifier = modifier) {
        TaskList(modifier, taskUiState.taskList)
    }
}

@Composable
fun TaskList(
    modifier: Modifier = Modifier, taskList: List<Task>
) {
    LazyColumn {
        items(taskList) { task ->
            TaskItem(modifier, task)
        }
    }
}

@Composable
fun TaskItem(
    modifier: Modifier = Modifier, task: Task
) {
    Column {
        ListItem(headlineText = { Text(text = task.title) })
        Divider()
    }
}
