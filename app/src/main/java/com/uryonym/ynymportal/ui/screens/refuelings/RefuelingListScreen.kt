package com.uryonym.ynymportal.ui.screens.refuelings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Update
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.uryonym.ynymportal.data.model.Refueling
import com.uryonym.ynymportal.ui.YnymPortalScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RefuelingListScreen(
    onNavigateRefuelingAdd: (String) -> Unit,
    onNavigateRefuelingEdit: (Refueling) -> Unit,
    onOpenDrawer: () -> Unit,
    viewModel: RefuelingListViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(topBar = {
        CenterAlignedTopAppBar(title = {
            Text(stringResource(id = YnymPortalScreen.RefuelingList.title))
        })
    }, bottomBar = {
        BottomAppBar(actions = {
            IconButton(onClick = onOpenDrawer) {
                Icon(imageVector = Icons.Filled.Menu, contentDescription = "メニュー")
            }
            IconButton(onClick = viewModel::refreshRefuelings) {
                Icon(imageVector = Icons.Filled.Update, contentDescription = "更新")
            }
        }, floatingActionButton = {
            uiState.selectedCar?.id?.let { carId ->
                FloatingActionButton(onClick = { onNavigateRefuelingAdd(carId) }) {
                    Icon(imageVector = Icons.Filled.Add, contentDescription = "追加")
                }
            }
        })
    }) { padding ->
        Column(
            modifier = Modifier.padding(padding)
        ) {
            ExposedDropdownMenuBox(
                expanded = uiState.carListExpanded,
                onExpandedChange = viewModel::onChangeCarListExpanded
            ) {
                TextField(
                    value = uiState.selectedCar?.name ?: "",
                    label = { Text("車両") },
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = uiState.carListExpanded) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor()
                )
                DropdownMenu(
                    expanded = uiState.carListExpanded,
                    onDismissRequest = { viewModel.onChangeCarListExpanded(false) },
                    modifier = Modifier.exposedDropdownSize()
                ) {
                    uiState.cars.forEach { car ->
                        DropdownMenuItem(
                            text = { Text(car.name) },
                            onClick = {
                                viewModel.onChangeSelectedCar(car)
                                viewModel.onChangeCarListExpanded(false)
                            }
                        )
                    }
                }
            }

            LazyColumn {
                items(items = uiState.refuelings) { refueling ->
                    Column {
                        ListItem(
                            headlineContent = { Text(text = refueling.refuelDateTime.toString()) },
                            modifier = Modifier.clickable {
                                onNavigateRefuelingEdit(refueling)
                            })
                        HorizontalDivider()
                    }
                }
            }
        }
    }
}
