package com.uryonym.ynymportal.ui.screens

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
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.uryonym.ynymportal.YnymPortalScreen

@Composable
fun AuthInfoListScreen(
    authInfoViewModel: AuthInfoViewModel,
    onNavigateAuthInfoAdd: () -> Unit,
    onNavigateAuthInfoEdit: () -> Unit,
    onOpenDrawer: () -> Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = {
                Text(stringResource(id = YnymPortalScreen.AuthInfoList.title))
            })
        },
        bottomBar = {
            BottomAppBar(
                actions = {
                    IconButton(onClick = onOpenDrawer) {
                        Icon(imageVector = Icons.Filled.Menu, contentDescription = "メニュー")
                    }
                },
                floatingActionButton = {
                    FloatingActionButton(onClick = onNavigateAuthInfoAdd) {
                        Icon(imageVector = Icons.Filled.Add, contentDescription = "追加")
                    }
                })
        }) { padding ->
        val authInfoList by authInfoViewModel.authInfoList.collectAsState()

        LazyColumn(modifier = Modifier.padding(padding)) {
            items(items = authInfoList) { authInfo ->
                Column {
                    ListItem(
                        headlineContent = { Text(text = authInfo.serviceName) },
                        leadingContent = {
                            Checkbox(checked = false, onCheckedChange = {})
                        },
                        modifier = Modifier.clickable {
                            authInfoViewModel.onClickAuthInfoItem(authInfo)
                            onNavigateAuthInfoEdit()
                        })
                    Divider()
                }
            }
        }
    }
}
