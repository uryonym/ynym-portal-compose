package com.uryonym.ynymportal.ui

import androidx.activity.compose.BackHandler
import androidx.annotation.StringRes
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.uryonym.ynymportal.R
import com.uryonym.ynymportal.ui.screens.cars.CarAddScreen
import com.uryonym.ynymportal.ui.screens.cars.CarEditScreen
import com.uryonym.ynymportal.ui.screens.cars.CarListScreen
import com.uryonym.ynymportal.ui.screens.confidentials.ConfidentialAddScreen
import com.uryonym.ynymportal.ui.screens.confidentials.ConfidentialEditScreen
import com.uryonym.ynymportal.ui.screens.confidentials.ConfidentialListScreen
import com.uryonym.ynymportal.ui.screens.login.LoginScreen
import com.uryonym.ynymportal.ui.screens.tasks.TaskAddScreen
import com.uryonym.ynymportal.ui.screens.tasks.TaskEditScreen
import com.uryonym.ynymportal.ui.screens.tasks.TaskListScreen
import kotlinx.coroutines.launch

sealed class YnymPortalScreen(val route: String, @StringRes val title: Int) {
    object Login : YnymPortalScreen(route = "login", title = R.string.login)
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

    object CarList :
        YnymPortalScreen(route = "carList", title = R.string.car_list)

    object CarAdd :
        YnymPortalScreen(route = "carAdd", title = R.string.add_car)

    object CarEdit : YnymPortalScreen(
        route = "carEdit/{carId}",
        title = R.string.edit_car
    ) {
        fun createRoute(carId: String): String {
            return "carEdit/$carId"
        }
    }
}

@Composable
fun YnymPortalApp(
    viewModel: YnymPortalAppViewModel = hiltViewModel()
) {
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

    NavHost(
        navController = navController,
        startDestination = YnymPortalScreen.Login.route
    ) {
        composable(route = YnymPortalScreen.Login.route) {
            LoginScreen()
        }
        composable(route = YnymPortalScreen.TaskList.route) {
            NavigationDrawer(
                drawerState = drawerState,
                navController = navController,
                scope = scope,
                viewModel = viewModel
            ) {
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
        }
        composable(route = YnymPortalScreen.TaskAdd.route) {
            NavigationDrawer(
                drawerState = drawerState,
                navController = navController,
                scope = scope,
                viewModel = viewModel
            ) {
                TaskAddScreen(
                    onNavigateBack = { navController.popBackStack() }
                )
            }
        }
        composable(route = YnymPortalScreen.TaskEdit.route) {
            NavigationDrawer(
                drawerState = drawerState,
                navController = navController,
                scope = scope,
                viewModel = viewModel
            ) {
                TaskEditScreen(
                    onNavigateBack = { navController.popBackStack() }
                )
            }
        }
        composable(route = YnymPortalScreen.ConfidentialList.route) {
            NavigationDrawer(
                drawerState = drawerState,
                navController = navController,
                scope = scope,
                viewModel = viewModel
            ) {
                ConfidentialListScreen(
                    onNavigateConfidentialAdd = {
                        navController.navigate(YnymPortalScreen.ConfidentialAdd.route)
                    },
                    onNavigateConfidentialEdit = { confidential ->
                        confidential.id?.let {
                            navController.navigate(
                                YnymPortalScreen.ConfidentialEdit.createRoute(it)
                            )
                        }
                    },
                    onOpenDrawer = { scope.launch { drawerState.open() } }
                )
            }
        }
        composable(route = YnymPortalScreen.ConfidentialAdd.route) {
            NavigationDrawer(
                drawerState = drawerState,
                navController = navController,
                scope = scope,
                viewModel = viewModel
            ) {
                ConfidentialAddScreen(
                    onNavigateBack = { navController.popBackStack() }
                )
            }
        }
        composable(route = YnymPortalScreen.ConfidentialEdit.route) {
            NavigationDrawer(
                drawerState = drawerState,
                navController = navController,
                scope = scope,
                viewModel = viewModel
            ) {
                ConfidentialEditScreen(
                    onNavigateBack = { navController.popBackStack() }
                )
            }
        }
        composable(route = YnymPortalScreen.CarList.route) {
            NavigationDrawer(
                drawerState = drawerState,
                navController = navController,
                scope = scope,
                viewModel = viewModel
            ) {
                CarListScreen(
                    onNavigateCarAdd = {
                        navController.navigate(YnymPortalScreen.CarAdd.route)
                    },
                    onNavigateCarEdit = { car ->
                        car.id?.let {
                            navController.navigate(
                                YnymPortalScreen.CarEdit.createRoute(it)
                            )
                        }
                    },
                    onOpenDrawer = { scope.launch { drawerState.open() } }
                )
            }
        }
        composable(route = YnymPortalScreen.CarAdd.route) {
            NavigationDrawer(
                drawerState = drawerState,
                navController = navController,
                scope = scope,
                viewModel = viewModel
            ) {
                CarAddScreen(
                    onNavigateBack = { navController.navigateUp() }
                )
            }
        }
        composable(route = YnymPortalScreen.CarEdit.route) {
            NavigationDrawer(
                drawerState = drawerState,
                navController = navController,
                scope = scope,
                viewModel = viewModel
            ) {
                CarEditScreen(
                    onNavigateBack = { navController.navigateUp() }
                )
            }
        }
    }

    val currentNavigate by viewModel.currentNavigate.collectAsStateWithLifecycle()

    val isUserSignedOut = viewModel.getAuthState().collectAsState().value
    if (isUserSignedOut) {
        navController.navigate(YnymPortalScreen.Login.route) {
            popUpTo(navController.graph.id) {
                inclusive = true
            }
        }
    } else {
        navController.navigate(currentNavigate) {
            popUpTo(navController.graph.id) {
                inclusive = true
            }
        }
    }
}