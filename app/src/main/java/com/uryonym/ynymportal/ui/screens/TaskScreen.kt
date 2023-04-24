package com.uryonym.ynymportal.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.LazyColumn
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
        Text(taskUiState.taskSize.toString())
    }
}