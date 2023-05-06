package com.uryonym.ynymportal.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.BottomAppBar
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
import com.uryonym.ynymportal.YnymPortalScreen

@Composable
fun AuthInfoEditScreen(
    authInfoViewModel: AuthInfoViewModel,
    onNavigateBack: () -> Unit,
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(stringResource(id = YnymPortalScreen.AuthInfoEdit.title))
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "戻る")
                    }
                },
                actions = {
                    TextButton(onClick = {
                        authInfoViewModel.onSaveEditAuthInfo()
                        onNavigateBack()
                    }) {
                        Text(text = "保存")
                    }
                })
        },
        bottomBar = {
            BottomAppBar(actions = {
                IconButton(onClick = {
                    authInfoViewModel.onDelete()
                    onNavigateBack()
                }) {
                    Icon(imageVector = Icons.Filled.Delete, contentDescription = "削除")
                }
            })
        }) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = authInfoViewModel.serviceName,
                label = { Text("サービス名") },
                onValueChange = { authInfoViewModel.onChangeServiceName(it) },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = authInfoViewModel.loginId,
                label = { Text("ログインID") },
                onValueChange = { authInfoViewModel.onChangeLoginId(it) },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = authInfoViewModel.password,
                label = { Text("パスワード") },
                onValueChange = { authInfoViewModel.onChangePassword(it) },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = authInfoViewModel.other,
                label = { Text("備考") },
                onValueChange = { authInfoViewModel.onChangeOther(it) },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
