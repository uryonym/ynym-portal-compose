package com.uryonym.ynymportal.ui.screens.confidentials

import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.input.TextFieldValue

@Composable
fun ConfidentialAddEditForm(
    serviceName: TextFieldValue,
    loginId: String,
    password: String,
    other: String,
    onChangeServiceName: (TextFieldValue) -> Unit,
    onChangeLoginId: (String) -> Unit,
    onChangePassword: (String) -> Unit,
    onChangeOther: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val focusRequester = remember { FocusRequester() }

    OutlinedTextField(
        value = serviceName,
        label = { Text("サービス名") },
        onValueChange = { onChangeServiceName(it) },
        singleLine = true,
        modifier = modifier.focusRequester(focusRequester)
    )
    OutlinedTextField(
        value = loginId,
        label = { Text("ログインID") },
        onValueChange = { onChangeLoginId(it) },
        singleLine = true,
        modifier = modifier
    )
    OutlinedTextField(
        value = password,
        label = { Text("パスワード") },
        onValueChange = { onChangePassword(it) },
        singleLine = true,
        modifier = modifier
    )
    OutlinedTextField(
        value = other,
        label = { Text("備考") },
        onValueChange = { onChangeOther(it) },
        singleLine = true,
        modifier = modifier
    )

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
}
