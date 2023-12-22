package com.uryonym.ynymportal.ui.screens.tasks

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.uryonym.ynymportal.navigation.YnymPortalScreen.TaskListListScreen
import com.uryonym.ynymportal.ui.components.DraggableItem
import com.uryonym.ynymportal.ui.components.dragContainer
import com.uryonym.ynymportal.ui.components.rememberDragDropState

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
                    Text(stringResource(id = TaskListListScreen.title))
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
        val listState = rememberLazyListState()
        val dragDropState = rememberDragDropState(listState) { from, to ->
            viewModel.onChangeListIndex(from, to)
        }

        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .dragContainer(dragDropState),
            state = listState
        ) {
            itemsIndexed(
                items = uiState.taskLists,
                key = { _, item -> item.id }
            ) { index, taskList ->
                DraggableItem(dragDropState, index) {
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
    title: TextFieldValue,
    onChangeTitle: (TextFieldValue) -> Unit,
    onSave: () -> Unit,
    onDelete: () -> Unit,
    onCloseModal: () -> Unit,
    isEdit: Boolean
) {
    val focusRequester = remember { FocusRequester() }

    ModalBottomSheet(onDismissRequest = onCloseModal) {
        Row {
            OutlinedTextField(
                value = title,
                label = { Text("タスクリスト名") },
                onValueChange = { onChangeTitle(it) },
                maxLines = 1,
                modifier = Modifier.focusRequester(focusRequester)
            )
            TextButton(onClick = onSave) { Text(text = "保存") }
            if (isEdit) {
                TextButton(onClick = onDelete) { Text(text = "削除") }
            }
        }
    }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
}
