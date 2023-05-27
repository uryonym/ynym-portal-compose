package com.uryonym.ynymportal.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.uryonym.ynymportal.data.Task
import com.uryonym.ynymportal.ui.YnymPortalScreen

@Composable
fun TaskListScreen(
    taskViewModel: TaskViewModel,
    onNavigateTaskAdd: () -> Unit,
    onNavigateTaskEdit: () -> Unit,
    onOpenDrawer: () -> Unit
) {
    Scaffold(topBar = {
        CenterAlignedTopAppBar(title = {
            Text(stringResource(id = YnymPortalScreen.TaskList.title))
        })
    }, bottomBar = {
        BottomAppBar(actions = {
            IconButton(onClick = onOpenDrawer) {
                Icon(
                    imageVector = Icons.Filled.Menu, contentDescription = "メニュー"
                )
            }
        }, floatingActionButton = {
            FloatingActionButton(onClick = onNavigateTaskAdd) {
                Icon(
                    imageVector = Icons.Filled.Add, contentDescription = "追加"
                )
            }
        })
    }) { padding ->
        val taskList by taskViewModel.taskList.collectAsStateWithLifecycle()

        LazyColumn(modifier = Modifier.padding(padding)) {
            items(items = taskList) { task ->
                Column {
                    TaskListItem(
                        task = task,
                        onNavigateTaskEdit = onNavigateTaskEdit,
                        taskViewModel = taskViewModel
                    )
                    Divider()
                }
            }
        }
    }
}

@Composable
fun TaskListItem(task: Task, onNavigateTaskEdit: () -> Unit, taskViewModel: TaskViewModel) {
    ListItem(
        headlineContent = {
            Column {
                Text(text = task.title)
                task.deadLine?.let {
                    Text(text = it.toString())
                }
            }
        },
        leadingContent = {
            Checkbox(
                checked = task.isComplete,
                onCheckedChange = {
                    taskViewModel.onSaveStatus(
                        currentTask = task,
                        status = it
                    )
                })
        },
        modifier = Modifier.clickable {
            taskViewModel.onClickTaskItem(task)
            onNavigateTaskEdit()
        })
}