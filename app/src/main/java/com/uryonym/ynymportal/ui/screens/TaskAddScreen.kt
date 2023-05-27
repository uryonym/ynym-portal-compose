package com.uryonym.ynymportal.ui.screens

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
import com.uryonym.ynymportal.ui.YnymPortalScreen
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@Composable
fun TaskAddScreen(
    taskViewModel: TaskViewModel,
    onNavigateBack: () -> Unit,
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(stringResource(id = YnymPortalScreen.TaskAdd.title))
                },
                navigationIcon = {
                    IconButton(onClick = {
                        taskViewModel.onClearState()
                        onNavigateBack()
                    }) {
                        Icon(imageVector = Icons.Filled.Close, contentDescription = "閉じる")
                    }
                },
                actions = {
                    TextButton(onClick = {
                        taskViewModel.onSaveNewTask()
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
            TaskCommonForm(taskViewModel = taskViewModel)
        }
    }
}

@Composable
fun TaskCommonForm(taskViewModel: TaskViewModel) {
    OutlinedTextField(
        value = taskViewModel.title,
        label = { Text("タスク") },
        onValueChange = { taskViewModel.onChangeTitle(it) },
        maxLines = 3,
        modifier = Modifier
            .padding(16.dp, 8.dp)
            .fillMaxWidth()
    )
    OutlinedTextField(
        value = taskViewModel.description,
        label = { Text("詳細") },
        onValueChange = { taskViewModel.onChangeDescription(it) },
        leadingIcon = {
            Icon(
                imageVector = Icons.Outlined.Description,
                contentDescription = "詳細"
            )
        },
        maxLines = 5,
        modifier = Modifier
            .padding(16.dp, 8.dp)
            .fillMaxWidth()
    )
    OutlinedTextField(
        value = taskViewModel.deadLine.toString(),
        label = { Text("期日") },
        onValueChange = { taskViewModel.onChangeDescription(it) },
        trailingIcon = {
            IconButton(onClick = { taskViewModel.onChangePicker(true) }) {
                Icon(
                    imageVector = Icons.Outlined.EditCalendar,
                    contentDescription = "期日",
                )
            }
        },
        singleLine = true,
        modifier = Modifier
            .padding(16.dp, 8.dp)
            .fillMaxWidth()
    )

    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = Clock.System.now().toEpochMilliseconds()
    )
    if (taskViewModel.showPicker) {
        DatePickerDialogComponent(
            datePickerState = datePickerState,
            onChangeDeadLine = { taskViewModel.onChangeDeadLine(it) },
            closePicker = { taskViewModel.onChangePicker(false) })
    }
}

@Composable
fun DatePickerDialogComponent(
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
