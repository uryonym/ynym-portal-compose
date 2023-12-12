package com.uryonym.ynymportal.ui.screens.cars

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue

@Composable
fun CarAddEditForm(
    name: TextFieldValue,
    maker: String,
    model: String,
    modelYear: String,
    licensePlate: String,
    tankCapacity: String,
    onChangeName: (TextFieldValue) -> Unit,
    onChangeMaker: (String) -> Unit,
    onChangeModel: (String) -> Unit,
    onChangeModelYear: (String) -> Unit,
    onChangeLicensePlate: (String) -> Unit,
    onChangeTankCapacity: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val focusRequester = remember { FocusRequester() }

    OutlinedTextField(
        value = name,
        label = { Text("名称") },
        onValueChange = { onChangeName(it) },
        singleLine = true,
        modifier = modifier.focusRequester(focusRequester)
    )
    OutlinedTextField(
        value = maker,
        label = { Text("メーカー") },
        onValueChange = { onChangeMaker(it) },
        singleLine = true,
        modifier = modifier
    )
    OutlinedTextField(
        value = model,
        label = { Text("モデル") },
        onValueChange = { onChangeModel(it) },
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        modifier = modifier
    )
    OutlinedTextField(
        value = modelYear,
        label = { Text("年式") },
        onValueChange = { onChangeModelYear(it) },
        singleLine = true,
        modifier = modifier
    )
    OutlinedTextField(
        value = licensePlate,
        label = { Text("ナンバープレート") },
        onValueChange = { onChangeLicensePlate(it) },
        singleLine = true,
        modifier = modifier
    )
    OutlinedTextField(
        value = tankCapacity,
        label = { Text("タンク容量") },
        onValueChange = { onChangeTankCapacity(it) },
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        modifier = modifier
    )

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
}
