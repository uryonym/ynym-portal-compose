package com.uryonym.ynymportal.ui.screens.confidentials

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.focusTarget
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.uryonym.ynymportal.navigation.YnymPortalScreen.ConfidentialAddScreen

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
                    Text(stringResource(id = ConfidentialAddScreen.title))
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
        val focusRequester = remember { FocusRequester() }
        val interactionSource = remember { MutableInteractionSource() }

        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .clickable(
                    interactionSource = interactionSource,
                    enabled = true,
                    indication = null,
                    onClick = { focusRequester.requestFocus() }
                )
                .focusRequester(focusRequester)
                .focusTarget(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ConfidentialAddEditForm(
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
