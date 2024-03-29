package com.uryonym.ynymportal.ui.screens.tasks

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.focusTarget
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.uryonym.ynymportal.navigation.YnymPortalScreen.TaskAddScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskAddScreen(
    onNavigateBack: () -> Unit,
    onHideKeyboard: () -> Unit,
    viewModel: TaskAddViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(stringResource(id = TaskAddScreen.title))
                },
                navigationIcon = {
                    IconButton(onClick = {
                        onHideKeyboard()
                        onNavigateBack()
                    }) {
                        Icon(imageVector = Icons.Filled.Close, contentDescription = "閉じる")
                    }
                },
                actions = {
                    TextButton(onClick = {
                        onHideKeyboard()
                        viewModel.onSaveNewTask()
                    }) {
                        Text(text = "保存")
                    }
                })
        }) { padding ->
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()
        val focusRequester = remember { FocusRequester() }
        val interactionSource = remember { MutableInteractionSource() }

        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .clickable(
                    interactionSource = interactionSource,
                    enabled = true,
                    indication = null,
                    onClick = { focusRequester.requestFocus() }
                )
                .focusRequester(focusRequester)
                .focusTarget()
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
    }
}
