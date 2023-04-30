package com.uryonym.ynymportal.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.uryonym.ynymportal.ui.YnymPortalScreen

@Composable
fun AddTaskScreen(
    navController: NavController,
    taskViewModel: TaskViewModel = viewModel(),
    modifier: Modifier = Modifier,
) {
    val taskUiState by taskViewModel.taskUiState.collectAsState()
    Scaffold(topBar = {
        CenterAlignedTopAppBar(title = {
            Text(stringResource(id = YnymPortalScreen.AddTask.title))
        }, navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "戻る")
            }
        }, actions = {
            TextButton(onClick = { navController.popBackStack() }) {
                Text(text = "保存")
            }
        })
    }) { innerPadding ->
        Box(modifier = modifier.padding(innerPadding)) {
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
}
