package com.uryonym.ynymportal.ui.screens.tasks

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ListAlt
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Update
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.uryonym.ynymportal.data.model.Task
import com.uryonym.ynymportal.navigation.YnymPortalScreen.TaskListScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskListScreen(
    onNavigateTaskListList: () -> Unit,
    onNavigateTaskAdd: (String) -> Unit,
    onNavigateTaskEdit: (String) -> Unit,
    onOpenDrawer: () -> Unit,
    viewModel: TaskListViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(topBar = {
        CenterAlignedTopAppBar(title = {
            Text(stringResource(id = TaskListScreen.title))
        })
    }, bottomBar = {
        BottomAppBar(actions = {
            IconButton(onClick = onOpenDrawer) {
                Icon(imageVector = Icons.Filled.Menu, contentDescription = "メニュー")
            }
            IconButton(onClick = onNavigateTaskListList) {
                Icon(imageVector = Icons.AutoMirrored.Filled.ListAlt, contentDescription = "タスクリスト")
            }
            IconButton(onClick = viewModel::refresh) {
                Icon(imageVector = Icons.Filled.Update, contentDescription = "更新")
            }
        }, floatingActionButton = {
            uiState.selectedTaskListId?.let {
                FloatingActionButton(onClick = { onNavigateTaskAdd(it) }) {
                    Icon(imageVector = Icons.Filled.Add, contentDescription = "追加")
                }
            }
        })
    }) { padding ->
        Column(
            modifier = Modifier.padding(padding)
        ) {
            uiState.selectedTaskListId?.let { selectedTaskListId ->
                val taskListIndex = uiState.taskLists.indexOfFirst { it.id == selectedTaskListId }

                ScrollableTabRow(
                    selectedTabIndex = taskListIndex,
                    edgePadding = 16.dp
                ) {
                    uiState.taskLists.forEachIndexed { index, taskList ->
                        Tab(
                            selected = taskListIndex == index,
                            onClick = { viewModel.onClickTaskListTab(index) },
                            text = { Text(text = taskList.name) }
                        )
                    }
                }

                LazyColumn {
                    items(items = uiState.tasks.filter { it.taskListId == uiState.selectedTaskListId && !it.isComplete }) { task ->
                        TaskListItem(
                            task = task,
                            onNavigateTaskEdit = onNavigateTaskEdit,
                            viewModel = viewModel
                        )
                        HorizontalDivider()
                    }

                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                                .fillMaxWidth()
                                .height(32.dp)
                        ) {
                            Text(
                                text = "Completed",
                                style = MaterialTheme.typography.titleSmall
                            )
                        }
                    }

                    items(items = uiState.tasks.filter { it.taskListId == uiState.selectedTaskListId && it.isComplete }) { task ->
                        TaskListItem(
                            task = task,
                            onNavigateTaskEdit = onNavigateTaskEdit,
                            viewModel = viewModel
                        )
                        HorizontalDivider()
                    }
                }
            }
        }
    }
}

@Composable
fun TaskListItem(task: Task, onNavigateTaskEdit: (String) -> Unit, viewModel: TaskListViewModel) {
    ListItem(
        headlineContent = { Text(text = task.title) },
        supportingContent = { task.deadLine?.let { Text(text = it.toString()) } },
        leadingContent = {
            Checkbox(
                checked = task.isComplete,
                onCheckedChange = {
                    viewModel.onSaveStatus(task = task, status = it)
                }
            )
        },
        modifier = Modifier.clickable { onNavigateTaskEdit(task.id) }
    )
}
