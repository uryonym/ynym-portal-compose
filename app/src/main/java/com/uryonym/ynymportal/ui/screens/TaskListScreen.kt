package com.uryonym.ynymportal.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
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
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.uryonym.ynymportal.YnymPortalScreen
import kotlinx.coroutines.launch

@Composable
fun TaskListScreen(
    taskViewModel: TaskViewModel,
    onNavigateTaskAdd: () -> Unit,
    onNavigateTaskEdit: () -> Unit
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    BackHandler(
        enabled = drawerState.isOpen
    ) {
        scope.launch {
            drawerState.close()
        }
    }
    ModalNavigationDrawer(drawerState = drawerState, drawerContent = {
        ModalDrawerSheet {
            Spacer(modifier = Modifier.height(12.dp))
            NavigationDrawerItem(
                label = { Text(text = "タスク") },
                selected = true,
                onClick = { scope.launch { drawerState.close() } },
                modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
            )
            NavigationDrawerItem(
                label = { Text(text = "認証情報") },
                selected = false,
                onClick = { scope.launch { drawerState.close() } },
                modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
            )
        }
    }, content = {
        Scaffold(topBar = {
            CenterAlignedTopAppBar(title = {
                Text(stringResource(id = YnymPortalScreen.TaskList.title))
            })
        }, bottomBar = {
            BottomAppBar(actions = {
                IconButton(onClick = { scope.launch { drawerState.open() } }) {
                    Icon(
                        imageVector = Icons.Filled.Menu, contentDescription = "メニュー"
                    )
                }
            }, floatingActionButton = {
                FloatingActionButton(onClick = onNavigateTaskAdd) {
                    Icon(
                        imageVector = Icons.Filled.Add, contentDescription = "追加"
                    )
                }
            })
        }) { padding ->
            val taskList by taskViewModel.taskList.collectAsState()

            LazyColumn(modifier = Modifier.padding(padding)) {
                items(items = taskList) { task ->
                    Column {
                        ListItem(headlineContent = { Text(text = task.title) }, leadingContent = {
                            Checkbox(checked = false, onCheckedChange = {})
                        }, modifier = Modifier.clickable {
                            taskViewModel.onClickTaskItem(task)
                            onNavigateTaskEdit()
                        })
                        Divider()
                    }
                }
            }
        }
    })
}
