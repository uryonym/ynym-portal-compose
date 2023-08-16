package com.uryonym.ynymportal.ui.screens.components

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime

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
    Column(
        modifier = modifier.padding(16.dp, 0.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            val localDateTime = refuelDateTime.toLocalDateTime(TimeZone.currentSystemDefault())

            ClickableOutlinedTextField(
                value = "${localDateTime.date}",
                label = { Text("給油日") },
                onValueChange = { onChangeRefuelDateTime(it.toInstant()) },
                onClick = { Log.d("test", "click date") },
                modifier = Modifier.weight(1f)
            )
            ClickableOutlinedTextField(
                value = "${localDateTime.hour}:${localDateTime.minute}",
                label = { Text("給油時間") },
                onValueChange = { onChangeRefuelDateTime(it.toInstant()) },
                onClick = { Log.d("test", "click time") },
                modifier = Modifier.weight(1f)
            )
        }

        OutlinedTextField(
            value = odometer.toString(),
            label = { Text("総走行距離") },
            onValueChange = { onChangeOdometer(it) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = fuelType,
            label = { Text("種類") },
            onValueChange = { onChangeFuelType(it) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = price.toString(),
            label = { Text("単価") },
            onValueChange = { onChangePrice(it) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = quantity.toString(),
            label = { Text("給油量") },
            onValueChange = { onChangeQuantity(it) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
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
}
