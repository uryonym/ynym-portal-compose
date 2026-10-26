package com.uryonym.ynymportal.ui.screens.cars

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.uryonym.ynymportal.navigation.YnymPortalScreen.CarListScreen
import com.uryonym.ynymportal.ui.extensions.requestFocusImeAware

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarListScreen(
    onNavigateBack: () -> Unit,
    viewModel: CarListViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(imageVector = Icons.Filled.Close, contentDescription = "閉じる")
                    }
                },
                title = {
                    Text(stringResource(id = CarListScreen.title))
                })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { viewModel.onChangeShowModal(true) }) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "追加")
            }
        }
    )
    { padding ->
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()

        LazyColumn(modifier = Modifier.padding(padding)) {
            items(items = uiState.cars) { car ->
                ListItem(
                    headlineContent = { Text(text = car.name) },
                    trailingContent = {
                        IconButton(onClick = { }) {
                            Icon(
                                imageVector = Icons.Outlined.Delete,
                                contentDescription = "削除"
                            )
                        }
                    },
                    modifier = Modifier.clickable {
                        viewModel.onChangeShowModal(true)
                    })
                HorizontalDivider()
            }
        }

        if (uiState.isShowModal) {
            InputCarModal(
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
                onSave = viewModel::onSave,
                onCloseModal = { viewModel.onChangeShowModal(false) }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputCarModal(
    name: TextFieldValue,
    maker: String,
    model: String,
    modelYear: String,
    licensePlate: String,
    tankCapacity: String,
    onChangeName: (TextFieldValue) -> Unit,
    onChangeMaker: (String) -> Unit,
    onChangeModel: (String) -> Unit,
    onChangeModelYear: (String) -> Unit,
    onChangeLicensePlate: (String) -> Unit,
    onChangeTankCapacity: (String) -> Unit,
    onSave: () -> Unit,
    onCloseModal: () -> Unit,
    modifier: Modifier = Modifier
) {
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        focusRequester.requestFocusImeAware()
    }

    ModalBottomSheet(onDismissRequest = onCloseModal) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                OutlinedTextField(
                    value = name,
                    label = { Text("名称") },
                    onValueChange = { onChangeName(it) },
                    singleLine = true,
                    modifier = modifier.focusRequester(focusRequester)
                )
                TextButton(onClick = onSave) { Text(text = "保存") }
            }
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
    }
}
