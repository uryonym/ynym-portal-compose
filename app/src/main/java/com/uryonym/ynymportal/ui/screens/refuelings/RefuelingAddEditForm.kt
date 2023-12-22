package com.uryonym.ynymportal.ui.screens.refuelings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Checkbox
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.focusTarget
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.uryonym.ynymportal.ui.components.ClickableOutlinedTextField
import com.uryonym.ynymportal.ui.components.DatePickerDialogComponent
import com.uryonym.ynymportal.ui.components.TimePickerDialog
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
    val focusRequesterForm = remember { FocusRequester() }
    val focusRequesterScreen = remember { FocusRequester() }
    val interactionSource = remember { MutableInteractionSource() }
    val localDateTime = refuelDateTime.toLocalDateTime(TimeZone.currentSystemDefault())

    Column(
        modifier = modifier
            .fillMaxSize()
            .clickable(
                interactionSource = interactionSource,
                enabled = true,
                indication = null,
                onClick = { focusRequesterScreen.requestFocus() }
            )
            .focusRequester(focusRequesterScreen)
            .focusTarget()
            .padding(16.dp, 0.dp),
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

            val hour = localDateTime.hour.toString().padStart(2, '0')
            val minute = localDateTime.minute.toString().padStart(2, '0')
            ClickableOutlinedTextField(
                value = "${hour}:${minute}",
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
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequesterForm)
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
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = fuelTypeListExtended)
                },
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
            Row(modifier = Modifier.weight(1f)) {
                Text(text = "給油量： ")
                Text(text = if (quantity == null) "" else String.format("%.2f", quantity))
                Text(text = "L")
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .weight(1f)
                    .toggleable(
                        value = fullFlag,
                        onValueChange = onChangeFullFlag,
                        role = Role.Checkbox
                    )
            ) {
                Checkbox(checked = fullFlag, onCheckedChange = null)
                Text(text = "満タン", modifier = Modifier.padding(start = 16.dp))
            }
        }

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

    LaunchedEffect(Unit) {
        focusRequesterForm.requestFocus()
    }
}
