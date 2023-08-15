package com.uryonym.ynymportal.ui.screens.refuelings

import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.uryonym.ynymportal.ui.YnymPortalScreen
import com.uryonym.ynymportal.ui.screens.components.RefuelingAddEditForm

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RefuelingAddScreen(
    onNavigateBack: () -> Unit,
    viewModel: RefuelingAddViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(stringResource(id = YnymPortalScreen.RefuelingAdd.title))
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(imageVector = Icons.Filled.Close, contentDescription = "閉じる")
                    }
                },
                actions = {
                    TextButton(onClick = viewModel::onSaveNewRefueling) {
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
            RefuelingAddEditForm(
                refuelDateTime = uiState.refuelDateTime,
                odometer = uiState.odometer,
                fuelType = uiState.fuelType,
                price = uiState.price,
                quantity = uiState.quantity,
                fullFlag = uiState.fullFlag,
                gasStand = uiState.gasStand,
                onChangeRefuelDateTime = viewModel::onChangeRefuelDateTime,
                onChangeOdometer = viewModel::onChangeOdometer,
                onChangeFuelType = viewModel::onChangeFuelType,
                onChangePrice = viewModel::onChangePrice,
                onChangeQuantity = viewModel::onChangeQuantity,
                onChangeFullFlag = viewModel::onChangeFullFlag,
                onChangeGasStand = viewModel::onChangeGasStand,
                modifier = Modifier
                    .padding(16.dp, 8.dp)
                    .fillMaxWidth()
            )
        }

        LaunchedEffect(uiState.isRefuelingSaved) {
            if (uiState.isRefuelingSaved) {
                onNavigateBack()
            }
        }
    }
}

