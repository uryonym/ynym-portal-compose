package com.uryonym.ynymportal.ui.screens.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RefuelingAddEditForm(
    refuelDateTime: Instant,
    odometer: Int?,
    fuelTypeListExtended: Boolean,
    fuelTypeList: List<String>,
    fuelType: String,
    price: Int?,
    totalCost: Int?,
    fullFlag: Boolean,
    gasStand: String,
    quantity: Float?,
    isShowDatePicker: Boolean,
    isShowTimePicker: Boolean,
    onChangeRefuelDate: (LocalDate) -> Unit,
    onChangeRefuelTime: (LocalTime) -> Unit,
    onChangeOdometer: (String) -> Unit,
    onChangeFuelTypeListExtended: (Boolean) -> Unit,
    onChangeFuelType: (String) -> Unit,
    onChangePrice: (String) -> Unit,
    onChangeTotalCost: (String) -> Unit,
    onChangeFullFlag: (Boolean) -> Unit,
    onChangeGasStand: (String) -> Unit,
    onChangeShowDatePicker: (Boolean) -> Unit,
    onChangeShowTimePicker: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val localDateTime = refuelDateTime.toLocalDateTime(TimeZone.currentSystemDefault())

    Column(
        modifier = modifier.padding(16.dp, 0.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            ClickableOutlinedTextField(
                value = "${localDateTime.date}",
                label = { Text("給油日") },
                onValueChange = {},
                onClick = { onChangeShowDatePicker(true) },
                modifier = Modifier.weight(1f)
            )
            ClickableOutlinedTextField(
                value = "${localDateTime.hour}:${localDateTime.minute}",
                label = { Text("給油時間") },
                onValueChange = {},
                onClick = { onChangeShowTimePicker(true) },
                modifier = Modifier.weight(1f)
            )
        }

        OutlinedTextField(
            value = odometer?.toString() ?: "",
            label = { Text("総走行距離") },
            onValueChange = { onChangeOdometer(it) },
            singleLine = true,
            suffix = { Text("km") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        ExposedDropdownMenuBox(
            expanded = fuelTypeListExtended,
            onExpandedChange = onChangeFuelTypeListExtended
        ) {
            OutlinedTextField(
                value = fuelType,
                label = { Text("種類") },
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = fuelTypeListExtended) },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor()
            )
            DropdownMenu(
                expanded = fuelTypeListExtended,
                onDismissRequest = { onChangeFuelTypeListExtended(false) },
                modifier = Modifier.exposedDropdownSize()
            ) {
                fuelTypeList.forEach { fuelType ->
                    DropdownMenuItem(
                        text = { Text(fuelType) },
                        onClick = {
                            onChangeFuelType(fuelType)
                            onChangeFuelTypeListExtended(false)
                        }
                    )
                }
            }
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = price?.toString() ?: "",
                label = { Text("単価") },
                onValueChange = { onChangePrice(it) },
                singleLine = true,
                suffix = { Text("円/L") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.weight(1f)
            )
            OutlinedTextField(
                value = totalCost?.toString() ?: "",
                label = { Text("総費用") },
                onValueChange = { onChangeTotalCost(it) },
                singleLine = true,
                suffix = { Text("円") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.weight(1.5f)
            )
        }

        Row {
            Text(text = "給油量： ")
            Text(text = quantity?.toString() ?: "")
            Text(text = "L")
        }

        OutlinedTextField(
            value = fullFlag.toString(),
            label = { Text("満タン") },
            onValueChange = { onChangeFullFlag(it.toBoolean()) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = gasStand,
            label = { Text("ガソリンスタンド") },
            onValueChange = { onChangeGasStand(it) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
    }

    if (isShowDatePicker) {
        val datePickerState = rememberDatePickerState(
            initialSelectedDateMillis = refuelDateTime.toEpochMilliseconds()
        )

        DatePickerDialogComponent(
            datePickerState = datePickerState,
            onChangeDeadLine = { onChangeRefuelDate(it) },
            closePicker = { onChangeShowDatePicker(false) })
    }

    if (isShowTimePicker) {
        val timePickerState = rememberTimePickerState(
            initialHour = localDateTime.hour,
            initialMinute = localDateTime.minute
        )

        TimePickerDialog(
            onCancel = { onChangeShowTimePicker(false) },
            onConfirm = {
                onChangeRefuelTime(
                    LocalTime(
                        hour = timePickerState.hour,
                        minute = timePickerState.minute
                    )
                )
                onChangeShowTimePicker(false)
            }
        ) {
            TimePicker(state = timePickerState)
        }
    }
}
