package com.uryonym.ynymportal.ui.screens.confidentials

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.uryonym.ynymportal.ui.YnymPortalScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfidentialAddScreen(
    onNavigateBack: () -> Unit,
    viewModel: ConfidentialAddViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(stringResource(id = YnymPortalScreen.ConfidentialAdd.title))
                },
                navigationIcon = {
                    IconButton(onClick = {
                        onNavigateBack()
                    }) {
                        Icon(imageVector = Icons.Filled.Close, contentDescription = "閉じる")
                    }
                },
                actions = {
                    TextButton(onClick = viewModel::onSaveNewConfidential) {
                        Text(text = "保存")
                    }
                }
            )
        }
    ) { padding ->
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()

        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ConfidentialAddForm(
                serviceName = uiState.serviceName,
                loginId = uiState.loginId,
                password = uiState.password,
                other = uiState.other,
                onChangeServiceName = viewModel::onChangeServiceName,
                onChangeLoginId = viewModel::onChangeLoginId,
                onChangePassword = viewModel::onChangePassword,
                onChangeOther = viewModel::onChangeOther,
                modifier = Modifier
                    .padding(16.dp, 8.dp)
                    .fillMaxWidth()
            )
        }

        LaunchedEffect(uiState.isConfidentialSaved) {
            if (uiState.isConfidentialSaved) {
                onNavigateBack()
            }
        }
    }
}

@Composable
fun ConfidentialAddForm(
    serviceName: String,
    loginId: String,
    password: String,
    other: String,
    onChangeServiceName: (String) -> Unit,
    onChangeLoginId: (String) -> Unit,
    onChangePassword: (String) -> Unit,
    onChangeOther: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = serviceName,
        label = { Text("サービス名") },
        onValueChange = { onChangeServiceName(it) },
        singleLine = true,
        modifier = modifier
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
}