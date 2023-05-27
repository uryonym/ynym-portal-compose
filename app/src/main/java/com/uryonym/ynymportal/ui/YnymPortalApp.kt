package com.uryonym.ynymportal.ui

import androidx.activity.compose.BackHandler
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.uryonym.ynymportal.R
import com.uryonym.ynymportal.ui.screens.AuthInfoAddScreen
import com.uryonym.ynymportal.ui.screens.AuthInfoEditScreen
import com.uryonym.ynymportal.ui.screens.AuthInfoListScreen
import com.uryonym.ynymportal.ui.screens.AuthInfoViewModel
import com.uryonym.ynymportal.ui.screens.TaskAddScreen
import com.uryonym.ynymportal.ui.screens.TaskEditScreen
import com.uryonym.ynymportal.ui.screens.TaskListScreen
import com.uryonym.ynymportal.ui.screens.TaskViewModel
import kotlinx.coroutines.launch

enum class YnymPortalScreen(@StringRes val title: Int) {
    TaskList(title = R.string.task_list),
    TaskAdd(title = R.string.add_task),
    TaskEdit(title = R.string.edit_task),
    AuthInfoList(title = R.string.auth_info_list),
    AuthInfoAdd(title = R.string.add_auth_infok),
    AuthInfoEdit(title = R.string.edit_auth_info)
}

@Composable
fun YnymPortalApp() {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val taskViewModel: TaskViewModel = viewModel()
    val authInfoViewModel: AuthInfoViewModel = viewModel()

    BackHandler(
        enabled = drawerState.isOpen
    ) {
        scope.launch {
            drawerState.close()
        }
    }
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Spacer(modifier = Modifier.height(12.dp))
                NavigationDrawerItem(
                    label = { Text(text = "タスク") },
                    selected = false,
                    onClick = {
                        navController.navigate(YnymPortalScreen.TaskList.name) {
                            launchSingleTop = true
                            popUpTo(YnymPortalScreen.TaskList.name)
                        }
                        scope.launch {
                            drawerState.close()
                        }
                    },
                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                )
                NavigationDrawerItem(
                    label = { Text(text = "認証情報") },
                    selected = false,
                    onClick = {
                        navController.navigate(YnymPortalScreen.AuthInfoList.name) {
                            launchSingleTop = true
                            popUpTo(YnymPortalScreen.TaskList.name)
                        }
                        scope.launch {
                            drawerState.close()
                        }
                    },
                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                )
            }
        }, content = {
            NavHost(
                navController = navController,
                startDestination = YnymPortalScreen.TaskList.name
            ) {
                composable(route = YnymPortalScreen.TaskList.name) {
                    TaskListScreen(
                        taskViewModel = taskViewModel,
                        onNavigateTaskAdd = { navController.navigate(YnymPortalScreen.TaskAdd.name) },
                        onNavigateTaskEdit = { navController.navigate(YnymPortalScreen.TaskEdit.name) },
                        onOpenDrawer = { scope.launch { drawerState.open() } }
                    )
                }
                composable(route = YnymPortalScreen.TaskAdd.name) {
                    TaskAddScreen(
                        taskViewModel = taskViewModel,
                        onNavigateBack = { navController.navigateUp() }
                    )
                }
                composable(route = YnymPortalScreen.TaskEdit.name) {
                    TaskEditScreen(
                        taskViewModel = taskViewModel,
                        onNavigateBack = { navController.navigateUp() }
                    )
                }
                composable(route = YnymPortalScreen.AuthInfoList.name) {
                    AuthInfoListScreen(
                        authInfoViewModel = authInfoViewModel,
                        onNavigateAuthInfoAdd = {
                            navController.navigate(
                                YnymPortalScreen.AuthInfoAdd.name
                            )
                        },
                        onNavigateAuthInfoEdit = {
                            navController.navigate(
                                YnymPortalScreen.AuthInfoEdit.name
                            )
                        },
                        onOpenDrawer = { scope.launch { drawerState.open() } }
                    )
                }
                composable(route = YnymPortalScreen.AuthInfoAdd.name) {
                    AuthInfoAddScreen(
                        authInfoViewModel = authInfoViewModel,
                        onNavigateBack = { navController.navigateUp() }
                    )
                }
                composable(route = YnymPortalScreen.AuthInfoEdit.name) {
                    AuthInfoEditScreen(
                        authInfoViewModel = authInfoViewModel,
                        onNavigateBack = { navController.navigateUp() }
                    )
                }
            }
        }
    )
}