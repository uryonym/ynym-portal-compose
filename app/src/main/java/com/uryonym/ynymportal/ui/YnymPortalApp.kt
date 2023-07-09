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
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.uryonym.ynymportal.R
import com.uryonym.ynymportal.ui.screens.confidentials.ConfidentialAddScreen
import com.uryonym.ynymportal.ui.screens.confidentials.ConfidentialEditScreen
import com.uryonym.ynymportal.ui.screens.confidentials.ConfidentialListScreen
import com.uryonym.ynymportal.ui.screens.tasks.TaskAddScreen
import com.uryonym.ynymportal.ui.screens.tasks.TaskEditScreen
import com.uryonym.ynymportal.ui.screens.tasks.TaskListScreen
import kotlinx.coroutines.launch

sealed class YnymPortalScreen(val route: String, @StringRes val title: Int) {
    object TaskList : YnymPortalScreen(route = "taskList", title = R.string.task_list)
    object TaskAdd : YnymPortalScreen(route = "taskAdd", title = R.string.add_task)
    object TaskEdit : YnymPortalScreen(route = "taskEdit/{taskId}", title = R.string.edit_task) {
        fun createRoute(taskId: String): String {
            return "taskEdit/$taskId"
        }
    }

    object ConfidentialList :
        YnymPortalScreen(route = "confidentialList", title = R.string.confidential_list)

    object ConfidentialAdd :
        YnymPortalScreen(route = "confidentialAdd", title = R.string.add_confidential)

    object ConfidentialEdit : YnymPortalScreen(
        route = "confidentialEdit/{confidentialId}",
        title = R.string.edit_confidential
    ) {
        fun createRoute(confidentialId: String): String {
            return "confidentialEdit/$confidentialId"
        }
    }
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
                        navController.navigate(YnymPortalScreen.ConfidentialList.route) {
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
                        onNavigateTaskEdit = { task ->
                            task.id?.let {
                                navController.navigate(YnymPortalScreen.TaskEdit.createRoute(it))
                            }
                        },
                        onOpenDrawer = { scope.launch { drawerState.open() } }
                    )
                }
                composable(route = YnymPortalScreen.TaskAdd.route) {
                    TaskAddScreen(
                        onTaskSave = {
                            navController.navigate(YnymPortalScreen.TaskList.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    inclusive = true
                                }
                            }
                        },
                        onNavigateBack = { navController.navigateUp() }
                    )
                }
                composable(route = YnymPortalScreen.TaskEdit.route) {
                    TaskEditScreen(
                        onTaskUpdate = {
                            navController.navigate(YnymPortalScreen.TaskList.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    inclusive = true
                                }
                            }
                        },
                        onNavigateBack = { navController.navigateUp() }
                    )
                }
                composable(route = YnymPortalScreen.ConfidentialList.route) {
                    ConfidentialListScreen(
                        onNavigateConfidentialAdd = {
                            navController.navigate(
                                YnymPortalScreen.ConfidentialAdd.route
                            )
                        },
                        onNavigateConfidentialEdit = { confidential ->
                            confidential.id?.let {
                                navController.navigate(
                                    YnymPortalScreen.ConfidentialEdit.createRoute(
                                        it
                                    )
                                )
                            }
                        },
                        onOpenDrawer = { scope.launch { drawerState.open() } }
                    )
                }
                composable(route = YnymPortalScreen.ConfidentialAdd.route) {
                    ConfidentialAddScreen(
                        onConfidentialSave = {
                            navController.navigate(YnymPortalScreen.ConfidentialList.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    inclusive = true
                                }
                            }
                        },
                        onNavigateBack = { navController.navigateUp() }
                    )
                }
                composable(route = YnymPortalScreen.ConfidentialEdit.route) {
                    ConfidentialEditScreen(
                        onConfidentialUpdate = {
                            navController.navigate(YnymPortalScreen.ConfidentialList.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    inclusive = true
                                }
                            }
                        },
                        onNavigateBack = { navController.navigateUp() }
                    )
                }
            }
        }
    )
}