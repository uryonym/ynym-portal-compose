package com.uryonym.ynymportal.ui.screens.cars

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Update
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.uryonym.ynymportal.navigation.YnymPortalScreen.CarListScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarListScreen(
    onNavigateCarAdd: () -> Unit,
    onNavigateCarEdit: (String) -> Unit,
    onOpenDrawer: () -> Unit,
    viewModel: CarListViewModel = hiltViewModel()
) {
    Scaffold(topBar = {
        CenterAlignedTopAppBar(title = {
            Text(stringResource(id = CarListScreen.title))
        })
    }, bottomBar = {
        BottomAppBar(actions = {
            IconButton(onClick = onOpenDrawer) {
                Icon(imageVector = Icons.Filled.Menu, contentDescription = "メニュー")
            }
            IconButton(onClick = viewModel::refresh) {
                Icon(imageVector = Icons.Filled.Update, contentDescription = "更新")
            }
        }, floatingActionButton = {
            FloatingActionButton(onClick = onNavigateCarAdd) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "追加")
            }
        })
    }) { padding ->
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()

        LazyColumn(modifier = Modifier.padding(padding)) {
            items(items = uiState.cars) { car ->
                Column {
                    ListItem(
                        headlineContent = { Text(text = car.name) },
                        modifier = Modifier.clickable {
                            onNavigateCarEdit(car.id)
                        })
                    HorizontalDivider()
                }
            }
        }
    }
}
