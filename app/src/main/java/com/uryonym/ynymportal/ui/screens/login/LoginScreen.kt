package com.uryonym.ynymportal.ui.screens.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.uryonym.ynymportal.ui.YnymPortalScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    onOpenDrawer: () -> Unit,
    viewModel: LoginViewModel = viewModel()
) {
    Scaffold(topBar = {
        CenterAlignedTopAppBar(title = {
            Text(stringResource(id = YnymPortalScreen.Login.title))
        })
    }, bottomBar = {
        BottomAppBar(actions = {
            IconButton(onClick = onOpenDrawer) {
                Icon(imageVector = Icons.Filled.Menu, contentDescription = "メニュー")
            }
        })
    }) { padding ->
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()

        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LoginForm(
                email = uiState.email,
                password = uiState.password,
                onChangeEmail = viewModel::onChangeEmail,
                onChangePassword = viewModel::onChangePassword
            )

            Button(onClick = viewModel::onClickLogin) {
                Text(text = "Googleでログイン")
            }
        }
    }
}

@Composable
fun LoginForm(
    email: String,
    password: String,
    onChangeEmail: (String) -> Unit,
    onChangePassword: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = email,
        label = { Text("メールアドレス") },
        onValueChange = { onChangeEmail(it) },
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
}
