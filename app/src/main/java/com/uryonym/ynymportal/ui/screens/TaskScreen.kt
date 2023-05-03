package com.uryonym.ynymportal.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.uryonym.ynymportal.YnymPortalScreen
import com.uryonym.ynymportal.data.Task

@Composable
fun TaskScreen(
    navController: NavController,
    taskViewModel: TaskViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    Scaffold(topBar = {
        CenterAlignedTopAppBar(title = {
            Text(stringResource(id = YnymPortalScreen.TaskList.title))
        })
    }, floatingActionButton = {
        FloatingActionButton(onClick = { navController.navigate(YnymPortalScreen.AddTask.name) }) {
            Icon(imageVector = Icons.Filled.Add, contentDescription = "追加")
        }
    }) { innerPadding ->
        Box(modifier = modifier.padding(innerPadding)) {}
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
        ListItem(headlineContent = { Text(text = task.title) }, leadingContent = {
            Checkbox(checked = false, onCheckedChange = {})
        })
        Divider()
    }
}
