package com.uryonym.ynymportal.ui.screens.cars

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.BottomAppBar
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.uryonym.ynymportal.ui.YnymPortalScreen
import com.uryonym.ynymportal.ui.screens.components.DeleteConfirmDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarEditScreen(
    onNavigateBack: () -> Unit,
    viewModel: CarEditViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(stringResource(id = YnymPortalScreen.CarEdit.title))
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "戻る")
                    }
                },
                actions = {
                    TextButton(onClick = viewModel::onSaveEditCar) {
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

        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CarEditForm(
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

@Composable
private fun CarEditForm(
    name: String,
    maker: String,
    model: String,
    modelYear: String,
    licensePlate: String,
    tankCapacity: String,
    onChangeName: (String) -> Unit,
    onChangeMaker: (String) -> Unit,
    onChangeModel: (String) -> Unit,
    onChangeModelYear: (String) -> Unit,
    onChangeLicensePlate: (String) -> Unit,
    onChangeTankCapacity: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = name,
        label = { Text("名称") },
        onValueChange = { onChangeName(it) },
        singleLine = true,
        modifier = modifier
    )
    OutlinedTextField(
        value = maker,
        label = { Text("メーカー") },
        onValueChange = { onChangeMaker(it) },
        singleLine = true,
        modifier = modifier
    )
    OutlinedTextField(
        value = model,
        label = { Text("モデル") },
        onValueChange = { onChangeModel(it) },
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        modifier = modifier
    )
    OutlinedTextField(
        value = modelYear,
        label = { Text("年式") },
        onValueChange = { onChangeModelYear(it) },
        singleLine = true,
        modifier = modifier
    )
    OutlinedTextField(
        value = licensePlate,
        label = { Text("ナンバープレート") },
        onValueChange = { onChangeLicensePlate(it) },
        singleLine = true,
        modifier = modifier
    )
    OutlinedTextField(
        value = tankCapacity,
        label = { Text("タンク容量") },
        onValueChange = { onChangeTankCapacity(it) },
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        modifier = modifier
    )
}
