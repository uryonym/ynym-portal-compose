package com.uryonym.ynymportal.ui

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DirectionsCar
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Task
import androidx.compose.material3.DrawerState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun NavigationDrawer(
    drawerState: DrawerState,
    navController: NavHostController,
    scope: CoroutineScope,
    viewModel: YnymPortalAppViewModel,
    content: @Composable () -> Unit
) {
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Spacer(modifier = Modifier.height(12.dp))
                NavigationDrawerItem(
                    icon = { Icon(imageVector = Icons.Outlined.Task, contentDescription = "task") },
                    label = { Text(text = "タスク") },
                    selected = false,
                    onClick = {
                        viewModel.onChangeNavigate(YnymPortalScreen.TaskList.route)
//                        navController.navigate(YnymPortalScreen.TaskList.route) {
//                            launchSingleTop = true
//                            popUpTo(YnymPortalScreen.Login.route)
//                        }
                        scope.launch {
                            drawerState.close()
                        }
                    },
                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                )
                NavigationDrawerItem(
                    icon = { Icon(imageVector = Icons.Outlined.Lock, contentDescription = "lock") },
                    label = { Text(text = "認証情報") },
                    selected = false,
                    onClick = {
                        viewModel.onChangeNavigate(YnymPortalScreen.ConfidentialList.route)
//                        navController.navigate(YnymPortalScreen.ConfidentialList.route) {
//                            launchSingleTop = true
//                            popUpTo(YnymPortalScreen.Login.route)
//                        }
                        scope.launch {
                            drawerState.close()
                        }
                    },
                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                )
                NavigationDrawerItem(
                    icon = {
                        Icon(
                            imageVector = Icons.Outlined.DirectionsCar,
                            contentDescription = "car"
                        )
                    },
                    label = { Text(text = "車") },
                    selected = false,
                    onClick = {
                        viewModel.onChangeNavigate(YnymPortalScreen.CarList.route)
//                        navController.navigate(YnymPortalScreen.CarList.route) {
//                            launchSingleTop = true
//                            popUpTo(YnymPortalScreen.Login.route)
//                        }
                        scope.launch {
                            drawerState.close()
                        }
                    },
                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                )
                HorizontalDivider()
                NavigationDrawerItem(
                    label = { Text(text = "ログアウト") },
                    selected = false,
                    onClick = {
                        viewModel.onChangeNavigate("")
                        viewModel.signOut()
                        scope.launch {
                            drawerState.close()
                        }
                    }
                )
            }
        },
        content = content
    )
}