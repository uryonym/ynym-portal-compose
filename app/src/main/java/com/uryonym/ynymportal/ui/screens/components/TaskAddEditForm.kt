package com.uryonym.ynymportal.ui.screens.components

import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.EditCalendar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskAddEditForm(
    title: TextFieldValue,
    description: String,
    deadLine: LocalDate?,
    isShowPicker: Boolean,
    onChangeTitle: (TextFieldValue) -> Unit,
    onChangeDescription: (String) -> Unit,
    onChangeDeadLine: (LocalDate?) -> Unit,
    onChangeShowPicker: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val focusRequester = remember { FocusRequester() }

    OutlinedTextField(
        value = title,
        label = { Text("タスク") },
        onValueChange = { onChangeTitle(it) },
        maxLines = 3,
        modifier = modifier.focusRequester(focusRequester)
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
    ClickableOutlinedTextField(
        value = deadLine?.toString() ?: "",
        label = { Text(text = "期日") },
        onValueChange = {},
        onClick = { onChangeShowPicker(true) },
        leadingIcon = {
            Icon(
                imageVector = Icons.Outlined.EditCalendar,
                contentDescription = "期日",
            )
        },
        trailingIcon = {
            Icon(
                imageVector = Icons.Outlined.Delete,
                contentDescription = "削除",
                modifier = Modifier.clickable {
                    onChangeDeadLine(null)
                }
            )
        },
        modifier = modifier
    )

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

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
}
