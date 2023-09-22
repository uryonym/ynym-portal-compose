package com.uryonym.ynymportal.ui.screens.tasks

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.uryonym.ynymportal.ui.YnymPortalScreen
import com.uryonym.ynymportal.ui.screens.components.DeleteConfirmDialog
import com.uryonym.ynymportal.ui.screens.components.TaskAddEditForm

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskEditScreen(
    onNavigateBack: () -> Unit,
    viewModel: TaskEditViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(stringResource(id = YnymPortalScreen.TaskEdit.title))
                },
                navigationIcon = {
                    IconButton(onClick = { onNavigateBack() }) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "戻る")
                    }
                },
                actions = {
                    TextButton(onClick = viewModel::onSaveEditTask) {
                        Text(text = "保存")
                    }
                })
        },
        bottomBar = {
            BottomAppBar(actions = {
                IconButton(onClick = { viewModel.onChangeShowDeleteDialog(true) }) {
                    Icon(imageVector = Icons.Filled.Delete, contentDescription = "削除")
                }
            })
        }) { padding ->
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()

        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .padding(16.dp, 8.dp)
                    .fillMaxWidth()
            ) {
                Text(text = "リスト名：")
                uiState.taskList?.name?.let {
                    Text(text = it)
                }
            }

            TaskAddEditForm(
                title = uiState.title,
                description = uiState.description,
                deadLine = uiState.deadLine,
                isShowPicker = uiState.isShowPicker,
                onChangeTitle = viewModel::onChangeTitle,
                onChangeDescription = viewModel::onChangeDescription,
                onChangeDeadLine = viewModel::onChangeDeadLine,
                onChangeShowPicker = viewModel::onChangeShowPicker,
                modifier = Modifier
                    .padding(16.dp, 8.dp)
                    .fillMaxWidth()
            )
        }

        LaunchedEffect(uiState.isTaskSaved) {
            if (uiState.isTaskSaved) {
                onNavigateBack()
            }
        }

        DeleteConfirmDialog(
            isShow = uiState.isShowDeleteDialog,
            onChangeShow = viewModel::onChangeShowDeleteDialog,
            onDelete = viewModel::onDelete
        )
    }
}
