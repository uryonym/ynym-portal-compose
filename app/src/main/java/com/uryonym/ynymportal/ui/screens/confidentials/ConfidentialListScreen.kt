package com.uryonym.ynymportal.ui.screens.confidentials

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.uryonym.ynymportal.data.Confidential
import com.uryonym.ynymportal.ui.YnymPortalScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfidentialListScreen(
    onNavigateConfidentialAdd: () -> Unit,
    onNavigateConfidentialEdit: (Confidential) -> Unit,
    onOpenDrawer: () -> Unit,
    viewModel: ConfidentialViewModel = viewModel()
) {
    Scaffold(topBar = {
        CenterAlignedTopAppBar(title = {
            Text(stringResource(id = YnymPortalScreen.ConfidentialList.title))
        })
    }, bottomBar = {
        BottomAppBar(actions = {
            IconButton(onClick = onOpenDrawer) {
                Icon(imageVector = Icons.Filled.Menu, contentDescription = "メニュー")
            }
        }, floatingActionButton = {
            FloatingActionButton(onClick = onNavigateConfidentialAdd) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "追加")
            }
        })
    }) { padding ->
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()

        LazyColumn(modifier = Modifier.padding(padding)) {
            items(items = uiState.confidentials) { confidential ->
                Column {
                    ListItem(
                        headlineContent = { Text(text = confidential.serviceName) },
                        leadingContent = {
                            Checkbox(checked = false, onCheckedChange = {})
                        },
                        modifier = Modifier.clickable {
                            onNavigateConfidentialEdit(confidential)
                        })
                    Divider()
                }
            }
        }
    }
}
