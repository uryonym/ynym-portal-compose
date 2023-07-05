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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.uryonym.ynymportal.R
import com.uryonym.ynymportal.ui.screens.AuthInfoAddScreen
import com.uryonym.ynymportal.ui.screens.AuthInfoEditScreen
import com.uryonym.ynymportal.ui.screens.AuthInfoListScreen
import com.uryonym.ynymportal.ui.screens.tasks.TaskAddScreen
import com.uryonym.ynymportal.ui.screens.tasks.TaskEditScreen
import com.uryonym.ynymportal.ui.screens.tasks.TaskListScreen
import kotlinx.coroutines.launch

sealed class YnymPortalScreen(val route: String, @StringRes val title: Int) {
    object TaskList: YnymPortalScreen(route = "taskList", title = R.string.task_list)
    object TaskAdd: YnymPortalScreen(route = "taskAdd", title = R.string.add_task)
    object TaskEdit: YnymPortalScreen(route = "taskEdit/{taskId}", title = R.string.edit_task) {
        fun createRoute(taskId: String): String {
            return "taskEdit/$taskId"
        }
    }
    object AuthInfoList: YnymPortalScreen(route = "authInfoList", title = R.string.auth_info_list)
    object AuthInfoAdd: YnymPortalScreen(route = "authInfoAdd", title = R.string.add_auth_info)
    object AuthInfoEdit: YnymPortalScreen(route = "authInfoEdit", title = R.string.edit_auth_info)
}

@Composable
fun YnymPortalApp() {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

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
                        navController.navigate(YnymPortalScreen.TaskList.route) {
                            launchSingleTop = true
                            popUpTo(YnymPortalScreen.TaskList.route)
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
                        navController.navigate(YnymPortalScreen.AuthInfoList.route) {
                            launchSingleTop = true
                            popUpTo(YnymPortalScreen.TaskList.route)
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
                startDestination = YnymPortalScreen.TaskList.route
            ) {
                composable(route = YnymPortalScreen.TaskList.route) {
                    TaskListScreen(
                        onNavigateTaskAdd = { navController.navigate(YnymPortalScreen.TaskAdd.route) },
                        onNavigateTaskEdit = { task -> task.id?.let { it ->
                            navController.navigate(YnymPortalScreen.TaskEdit.createRoute(it))
                        } },
                        onOpenDrawer = { scope.launch { drawerState.open() } }
                    )
                }
                composable(route = YnymPortalScreen.TaskAdd.route) {
                    TaskAddScreen(
                        onNavigateBack = { navController.navigateUp() }
                    )
                }
                composable(route = YnymPortalScreen.TaskEdit.route, ) {
                    TaskEditScreen(
                        onNavigateBack = { navController.navigateUp() }
                    )
                }
                composable(route = YnymPortalScreen.AuthInfoList.route) {
                    AuthInfoListScreen(
                        onNavigateAuthInfoAdd = {
                            navController.navigate(
                                YnymPortalScreen.AuthInfoAdd.route
                            )
                        },
                        onNavigateAuthInfoEdit = {
                            navController.navigate(
                                YnymPortalScreen.AuthInfoEdit.route
                            )
                        },
                        onOpenDrawer = { scope.launch { drawerState.open() } }
                    )
                }
                composable(route = YnymPortalScreen.AuthInfoAdd.route) {
                    AuthInfoAddScreen(
                        onNavigateBack = { navController.navigateUp() }
                    )
                }
                composable(route = YnymPortalScreen.AuthInfoEdit.route) {
                    AuthInfoEditScreen(
                        onNavigateBack = { navController.navigateUp() }
                    )
                }
            }
        }
    )
}