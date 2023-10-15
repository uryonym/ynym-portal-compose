package com.uryonym.ynymportal.ui.screens.tasks

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.uryonym.ynymportal.ui.YnymPortalScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskListListScreen(
    onNavigateBack: () -> Unit,
    viewModel: TaskListListViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(imageVector = Icons.Filled.Close, contentDescription = "閉じる")
                    }
                },
                title = {
                    Text(stringResource(id = YnymPortalScreen.TaskListList.title))
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { viewModel.onChangeShowModal(true) }) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "追加")
            }
        }
    ) { padding ->
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()

        LazyColumn(modifier = Modifier.padding(padding)) {
            items(items = uiState.taskLists) { taskList ->
                Column {
                    ListItem(
                        headlineContent = { Text(text = taskList.name) },
                        modifier = Modifier.clickable {
                            viewModel.setTaskList(taskList)
                            viewModel.onChangeShowModal(true)
                        })
                    HorizontalDivider()
                }
            }
        }

        if (uiState.isShowModal) {
            InputTaskListNameModal(
                title = uiState.taskListTitle,
                onChangeTitle = viewModel::onChangeTitle,
                onSave = viewModel::onSave,
                onDelete = viewModel::onDelete,
                onCloseModal = { viewModel.onChangeShowModal(false) },
                isEdit = uiState.currentTaskList != null
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputTaskListNameModal(
    title: String,
    onChangeTitle: (String) -> Unit,
    onSave: () -> Unit,
    onDelete: () -> Unit,
    onCloseModal: () -> Unit,
    isEdit: Boolean
) {
    ModalBottomSheet(onDismissRequest = onCloseModal) {
        Row {
            OutlinedTextField(
                value = title,
                label = { Text("タスクリスト名") },
                onValueChange = { onChangeTitle(it) },
                maxLines = 1
            )
            TextButton(onClick = onSave) { Text(text = "保存") }
            if (isEdit) {
                TextButton(onClick = onDelete) { Text(text = "削除") }
            }
        }
    }
}
