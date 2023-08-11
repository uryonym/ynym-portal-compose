package com.uryonym.ynymportal.ui.screens.tasks

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.EditCalendar
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.uryonym.ynymportal.ui.YnymPortalScreen
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskAddScreen(
    onNavigateBack: () -> Unit,
    viewModel: TaskAddViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(stringResource(id = YnymPortalScreen.TaskAdd.title))
                },
                navigationIcon = {
                    IconButton(onClick = {
                        onNavigateBack()
                    }) {
                        Icon(imageVector = Icons.Filled.Close, contentDescription = "閉じる")
                    }
                },
                actions = {
                    TextButton(onClick = viewModel::onSaveNewTask) {
                        Text(text = "保存")
                    }
                })
        }) { padding ->
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()

        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxWidth()
        ) {
            TaskAddForm(
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskAddForm(
    title: String,
    description: String,
    deadLine: LocalDate?,
    isShowPicker: Boolean,
    onChangeTitle: (String) -> Unit,
    onChangeDescription: (String) -> Unit,
    onChangeDeadLine: (LocalDate) -> Unit,
    onChangeShowPicker: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = title,
        label = { Text("タスク") },
        onValueChange = { onChangeTitle(it) },
        maxLines = 3,
        modifier = modifier
    )
    OutlinedTextField(
        value = description,
        label = { Text("詳細") },
        onValueChange = { onChangeDescription(it) },
        leadingIcon = {
            Icon(
                imageVector = Icons.Outlined.Description,
                contentDescription = "詳細"
            )
        },
        maxLines = 5,
        modifier = modifier
    )
    Row(
        modifier = modifier.padding(horizontal = 0.dp, vertical = 12.dp).clickable { onChangeShowPicker(true) }
    ) {
        Spacer(modifier = Modifier.size(12.dp))
        Icon(
            imageVector = Icons.Outlined.EditCalendar,
            contentDescription = "期日",
        )
        Spacer(modifier = Modifier.size(16.dp))
        Text(text = "期日")
        Spacer(modifier = Modifier.size(16.dp))
        Text(
            text = deadLine?.toString() ?: "yyyy-mm-dd",
        )
    }

    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = Clock.System.now().toEpochMilliseconds()
    )
    if (isShowPicker) {
        DatePickerDialogComponent(
            datePickerState = datePickerState,
            onChangeDeadLine = { onChangeDeadLine(it) },
            closePicker = { onChangeShowPicker(false) })
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DatePickerDialogComponent(
    datePickerState: DatePickerState,
    onChangeDeadLine: (LocalDate) -> Unit,
    closePicker: () -> Unit,
    modifier: Modifier = Modifier
) {
    DatePickerDialog(
        onDismissRequest = closePicker,
        confirmButton = {
            TextButton(onClick = {
                datePickerState.selectedDateMillis?.let {
                    onChangeDeadLine(
                        Instant.fromEpochMilliseconds(
                            it
                        ).toLocalDateTime(TimeZone.currentSystemDefault()).date
                    )
                }

                closePicker()
            }) {
                Text(text = "OK")
            }
        },
        dismissButton = {
            TextButton(onClick = { closePicker() }) {
                Text(text = "キャンセル")
            }
        },
        modifier = modifier
    ) {
        DatePicker(state = datePickerState)
    }
}
