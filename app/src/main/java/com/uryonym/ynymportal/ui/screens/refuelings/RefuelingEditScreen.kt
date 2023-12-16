package com.uryonym.ynymportal.ui.screens.refuelings

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.uryonym.ynymportal.navigation.YnymPortalScreen.RefuelingEditScreen
import com.uryonym.ynymportal.ui.screens.components.DeleteConfirmDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RefuelingEditScreen(
    onNavigateBack: () -> Unit,
    viewModel: RefuelingEditViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(stringResource(id = RefuelingEditScreen.title))
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "戻る"
                        )
                    }
                },
                actions = {
                    TextButton(onClick = viewModel::onSaveEditRefueling) {
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

        DeleteConfirmDialog(
            isShow = uiState.isShowDeleteDialog,
            onChangeShow = viewModel::onChangeShowDeleteDialog,
            onDelete = viewModel::onDelete
        )
    }
}
