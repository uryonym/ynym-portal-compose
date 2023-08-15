package com.uryonym.ynymportal.ui.screens.refuelings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
fun RefuelingEditScreen(
    onNavigateBack: () -> Unit,
    viewModel: RefuelingEditViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(stringResource(id = YnymPortalScreen.RefuelingEdit.title))
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "戻る")
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
                IconButton(onClick = viewModel::onDelete) {
                    Icon(imageVector = Icons.Filled.Delete, contentDescription = "削除")
                }
            })
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
