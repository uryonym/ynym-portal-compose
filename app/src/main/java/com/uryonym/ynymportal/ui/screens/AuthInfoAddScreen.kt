package com.uryonym.ynymportal.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.uryonym.ynymportal.ui.YnymPortalScreen

@Composable
fun AuthInfoAddScreen(
    authInfoViewModel: AuthInfoViewModel,
    onNavigateBack: () -> Unit,
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(stringResource(id = YnymPortalScreen.AuthInfoAdd.title))
                },
                navigationIcon = {
                    IconButton(onClick = {
                        authInfoViewModel.onClearState()
                        onNavigateBack()
                    }) {
                        Icon(imageVector = Icons.Filled.Close, contentDescription = "閉じる")
                    }
                },
                actions = {
                    TextButton(onClick = {
                        authInfoViewModel.onSaveNewAuthInfo()
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
            AuthInfoCommonForm(
                authInfoViewModel = authInfoViewModel,
                modifier = Modifier
                    .padding(16.dp, 8.dp)
                    .fillMaxWidth()
            )
        }
    }
}

@Composable
fun AuthInfoCommonForm(authInfoViewModel: AuthInfoViewModel, modifier: Modifier = Modifier) {
    OutlinedTextField(
        value = authInfoViewModel.serviceName,
        label = { Text("サービス名") },
        onValueChange = { authInfoViewModel.onChangeServiceName(it) },
        singleLine = true,
        modifier = modifier
    )
    OutlinedTextField(
        value = authInfoViewModel.loginId,
        label = { Text("ログインID") },
        onValueChange = { authInfoViewModel.onChangeLoginId(it) },
        singleLine = true,
        modifier = modifier
    )
    OutlinedTextField(
        value = authInfoViewModel.password,
        label = { Text("パスワード") },
        onValueChange = { authInfoViewModel.onChangePassword(it) },
        singleLine = true,
        modifier = modifier
    )
    OutlinedTextField(
        value = authInfoViewModel.other,
        label = { Text("備考") },
        onValueChange = { authInfoViewModel.onChangeOther(it) },
        singleLine = true,
        modifier = modifier
    )
}