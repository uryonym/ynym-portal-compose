package com.uryonym.ynymportal.ui.screens.cars

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.BottomAppBar
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
import com.uryonym.ynymportal.navigation.YnymPortalScreen.CarEditScreen
import com.uryonym.ynymportal.ui.screens.components.DeleteConfirmDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarEditScreen(
    onNavigateBack: () -> Unit,
    onHideKeyboard: () -> Unit,
    viewModel: CarEditViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(stringResource(id = CarEditScreen.title)) },
                navigationIcon = {
                    IconButton(onClick = {
                        onHideKeyboard()
                        onNavigateBack()
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "戻る"
                        )
                    }
                },
                actions = {
                    TextButton(onClick = {
                        onHideKeyboard()
                        viewModel.onSaveEditCar()
                    }) {
                        Text(text = "保存")
                    }
                }
            )
        },
        bottomBar = {
            BottomAppBar(actions = {
                IconButton(onClick = { viewModel.onChangeShowDeleteDialog(true) }) {
                    Icon(imageVector = Icons.Filled.Delete, contentDescription = "削除")
                }
            })
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
            CarAddEditForm(
                name = uiState.name,
                maker = uiState.maker,
                model = uiState.model,
                modelYear = uiState.modelYear.toString(),
                licensePlate = uiState.licensePlate,
                tankCapacity = uiState.tankCapacity.toString(),
                onChangeName = viewModel::onChangeName,
                onChangeMaker = viewModel::onChangeMaker,
                onChangeModel = viewModel::onChangeModel,
                onChangeModelYear = viewModel::onChangeModelYear,
                onChangeLicensePlate = viewModel::onChangeLicensePlate,
                onChangeTankCapacity = viewModel::onChangeTankCapacity,
                modifier = Modifier
                    .padding(16.dp, 8.dp)
                    .fillMaxWidth()
            )
        }

        LaunchedEffect(uiState.isCarSaved) {
            if (uiState.isCarSaved) {
                onNavigateBack()
            }
        }

        DeleteConfirmDialog(
            isShow = uiState.isShowDeleteDialog,
            onChangeShow = viewModel::onChangeShowDeleteDialog,
            onDelete = viewModel::onDelete
        )
    }
}
