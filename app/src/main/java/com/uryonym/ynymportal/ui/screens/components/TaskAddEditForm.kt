package com.uryonym.ynymportal.ui.screens.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.EditCalendar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskAddEditForm(
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
        modifier = modifier
            .padding(horizontal = 0.dp, vertical = 12.dp)
            .clickable { onChangeShowPicker(true) }
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

    if (isShowPicker) {
        val datePickerState = rememberDatePickerState(
            initialSelectedDateMillis = deadLine?.atStartOfDayIn(TimeZone.currentSystemDefault())
                ?.toEpochMilliseconds() ?: Clock.System.now().toEpochMilliseconds()
        )

        DatePickerDialogComponent(
            datePickerState = datePickerState,
            onChangeDeadLine = { onChangeDeadLine(it) },
            closePicker = { onChangeShowPicker(false) })
    }
}
