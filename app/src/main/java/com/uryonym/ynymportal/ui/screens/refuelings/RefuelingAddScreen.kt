package com.uryonym.ynymportal.ui.screens.refuelings

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.uryonym.ynymportal.navigation.YnymPortalScreen.RefuelingAddScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RefuelingAddScreen(
    onNavigateBack: () -> Unit,
    onHideKeyboard: () -> Unit,
    viewModel: RefuelingAddViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(stringResource(id = RefuelingAddScreen.title))
                },
                navigationIcon = {
                    IconButton(onClick = {
                        onHideKeyboard()
                        onNavigateBack()
                    }) {
                        Icon(imageVector = Icons.Filled.Close, contentDescription = "閉じる")
                    }
                },
                actions = {
                    TextButton(onClick = {
                        onHideKeyboard()
                        viewModel.onSaveNewRefueling()
                    }) {
                        Text(text = "保存")
                    }
                }
            )
        }
    ) { padding ->
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()

        RefuelingAddEditForm(
            refuelDateTime = uiState.refuelDateTime,
            odometer = uiState.odometer,
            fuelTypeListExtended = uiState.fuelTypeListExtended,
            fuelTypeList = uiState.fuelTypeList,
            fuelType = uiState.fuelType,
            price = uiState.price,
            totalCost = uiState.totalCost,
            fullFlag = uiState.fullFlag,
            gasStand = uiState.gasStand,
            quantity = uiState.quantity,
            isShowDatePicker = uiState.isShowDatePicker,
            isShowTimePicker = uiState.isShowTimePicker,
            onChangeRefuelDate = viewModel::onChangeRefuelDate,
            onChangeRefuelTime = viewModel::onChangeRefuelTime,
            onChangeOdometer = viewModel::onChangeOdometer,
            onChangeFuelTypeListExtended = viewModel::onChangeFuelTypeListExpanded,
            onChangeFuelType = viewModel::onChangeFuelType,
            onChangePrice = viewModel::onChangePrice,
            onChangeTotalCost = viewModel::onChangeTotalCost,
            onChangeFullFlag = viewModel::onChangeFullFlag,
            onChangeGasStand = viewModel::onChangeGasStand,
            onChangeShowDatePicker = viewModel::onChangeShowDatePicker,
            onChangeShowTimePicker = viewModel::onChangeShowTimePicker,
            modifier = Modifier
                .padding(padding)
        )

        LaunchedEffect(uiState.isRefuelingSaved) {
            if (uiState.isRefuelingSaved) {
                onNavigateBack()
            }
        }
    }
}

