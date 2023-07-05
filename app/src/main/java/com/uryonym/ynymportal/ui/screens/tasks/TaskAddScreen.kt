package com.uryonym.ynymportal.ui.screens.tasks

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.EditCalendar
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.uryonym.ynymportal.data.Task
import com.uryonym.ynymportal.ui.YnymPortalScreen
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@Composable
fun TaskAddScreen(
    onNavigateBack: () -> Unit,
    viewModel: TaskViewModel = viewModel()
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(stringResource(id = YnymPortalScreen.TaskAdd.title))
                },
                navigationIcon = {
                    IconButton(onClick = {
                        viewModel.onClearState()
                        onNavigateBack()
                    }) {
                        Icon(imageVector = Icons.Filled.Close, contentDescription = "閉じる")
                    }
                },
                actions = {
                    TextButton(onClick = {
                        viewModel.onSaveNewTask()
                        onNavigateBack()
                    }) {
                        Text(text = "保存")
                    }
                })
        }) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TaskCommonForm(
                viewModel = viewModel,
                task = Task(isComplete = false),
                modifier = Modifier
                    .padding(16.dp, 8.dp)
                    .fillMaxWidth()
            )
        }
    }
}

@Composable
fun TaskCommonForm(viewModel: TaskViewModel, task: Task?, modifier: Modifier = Modifier) {
    OutlinedTextField(
        value = viewModel.title,
        label = { Text("タスク") },
        onValueChange = { viewModel.onChangeTitle(it) },
        maxLines = 3,
        modifier = modifier
    )
    OutlinedTextField(
        value = viewModel.description,
        label = { Text("詳細") },
        onValueChange = { viewModel.onChangeDescription(it) },
        leadingIcon = {
            Icon(
                imageVector = Icons.Outlined.Description,
                contentDescription = "詳細"
            )
        },
        maxLines = 5,
        modifier = modifier
    )
    OutlinedTextField(
        value = viewModel.deadLine.toString(),
        label = { Text("期日") },
        onValueChange = { viewModel.onChangeDescription(it) },
        trailingIcon = {
            IconButton(onClick = { viewModel.onChangePicker(true) }) {
                Icon(
                    imageVector = Icons.Outlined.EditCalendar,
                    contentDescription = "期日",
                )
            }
        },
        singleLine = true,
        modifier = modifier
    )

    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = Clock.System.now().toEpochMilliseconds()
    )
    if (viewModel.showPicker) {
        DatePickerDialogComponent(
            datePickerState = datePickerState,
            onChangeDeadLine = { viewModel.onChangeDeadLine(it) },
            closePicker = { viewModel.onChangePicker(false) })
    }
}

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
