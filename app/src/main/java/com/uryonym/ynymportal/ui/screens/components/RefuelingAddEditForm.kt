package com.uryonym.ynymportal.ui.screens.components

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import kotlinx.datetime.Instant
import kotlinx.datetime.toInstant

@Composable
fun RefuelingAddEditForm(
    refuelDateTime: Instant,
    odometer: Int,
    fuelType: String,
    price: Int,
    quantity: Int,
    fullFlag: Boolean,
    gasStand: String,
    onChangeRefuelDateTime: (Instant) -> Unit,
    onChangeOdometer: (String) -> Unit,
    onChangeFuelType: (String) -> Unit,
    onChangePrice: (String) -> Unit,
    onChangeQuantity: (String) -> Unit,
    onChangeFullFlag: (Boolean) -> Unit,
    onChangeGasStand: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = refuelDateTime.toString(),
        label = { Text("給油日時") },
        onValueChange = { onChangeRefuelDateTime(it.toInstant()) },
        singleLine = true,
        modifier = modifier
    )
    OutlinedTextField(
        value = odometer.toString(),
        label = { Text("総走行距離") },
        onValueChange = { onChangeOdometer(it) },
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        modifier = modifier
    )
    OutlinedTextField(
        value = fuelType,
        label = { Text("種類") },
        onValueChange = { onChangeFuelType(it) },
        singleLine = true,
        modifier = modifier
    )
    OutlinedTextField(
        value = price.toString(),
        label = { Text("単価") },
        onValueChange = { onChangePrice(it) },
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        modifier = modifier
    )
    OutlinedTextField(
        value = quantity.toString(),
        label = { Text("給油量") },
        onValueChange = { onChangeQuantity(it) },
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        modifier = modifier
    )
    OutlinedTextField(
        value = fullFlag.toString(),
        label = { Text("満タン") },
        onValueChange = { onChangeFullFlag(it.toBoolean()) },
        singleLine = true,
        modifier = modifier
    )
    OutlinedTextField(
        value = gasStand,
        label = { Text("ガソリンスタンド") },
        onValueChange = { onChangeGasStand(it) },
        singleLine = true,
        modifier = modifier
    )
}
