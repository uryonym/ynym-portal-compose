package com.uryonym.ynymportal.ui.screens.components

import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@OptIn(ExperimentalMaterial3Api::class)
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
