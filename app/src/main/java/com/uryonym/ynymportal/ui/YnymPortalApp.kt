package com.uryonym.ynymportal.ui

import androidx.activity.compose.BackHandler
import androidx.annotation.StringRes
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.uryonym.ynymportal.ui.screens.refuelings.RefuelingAddScreen
import com.uryonym.ynymportal.ui.screens.refuelings.RefuelingEditScreen
import com.uryonym.ynymportal.ui.screens.refuelings.RefuelingListScreen
import com.uryonym.ynymportal.ui.screens.tasks.TaskAddScreen
import com.uryonym.ynymportal.ui.screens.tasks.TaskEditScreen
import com.uryonym.ynymportal.ui.screens.tasks.TaskListListScreen
import com.uryonym.ynymportal.ui.screens.tasks.TaskListScreen
import kotlinx.coroutines.launch

sealed class YnymPortalScreen(val route: String, @StringRes val title: Int) {
    data object Login : YnymPortalScreen(route = "login", title = R.string.login)
    data object TaskList : YnymPortalScreen(route = "taskList", title = R.string.task_list)
    data object TaskListList :
        YnymPortalScreen(route = "taskListList", title = R.string.task_list_list)

    data object TaskAdd :
        YnymPortalScreen(route = "taskAdd/{taskListId}", title = R.string.add_task) {
        fun createRoute(taskListId: String): String {
            return "taskAdd/$taskListId"
        }
    }

    data object TaskEdit :
        YnymPortalScreen(route = "taskEdit/{taskId}", title = R.string.edit_task) {
        fun createRoute(taskId: String): String {
            return "taskEdit/$taskId"
        }
    }

    data object ConfidentialList :
        YnymPortalScreen(route = "confidentialList", title = R.string.confidential_list)

    data object ConfidentialAdd :
        YnymPortalScreen(route = "confidentialAdd", title = R.string.add_confidential)

    data object ConfidentialEdit : YnymPortalScreen(
        route = "confidentialEdit/{confidentialId}",
        title = R.string.edit_confidential
    ) {
        fun createRoute(confidentialId: String): String {
            return "confidentialEdit/$confidentialId"
        }
    }

    data object CarList :
        YnymPortalScreen(route = "carList", title = R.string.car_list)

    data object CarAdd :
        YnymPortalScreen(route = "carAdd", title = R.string.add_car)

    data object CarEdit : YnymPortalScreen(
        route = "carEdit/{carId}",
        title = R.string.edit_car
    ) {
        fun createRoute(carId: String): String {
            return "carEdit/$carId"
        }
    }

    data object RefuelingList :
        YnymPortalScreen(route = "refuelingList", title = R.string.refueling_list)

    data object RefuelingAdd : YnymPortalScreen(
        route = "refuelingAdd/{carId}",
        title = R.string.add_refueling
    ) {
        fun createRoute(carId: String): String {
            return "refuelingAdd/$carId"
        }
    }

    data object RefuelingEdit : YnymPortalScreen(
        route = "refuelingEdit/{refuelingId}",
        title = R.string.edit_refueling
    ) {
        fun createRoute(refuelingId: String): String {
            return "refuelingEdit/$refuelingId"
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
                scope = scope,
                viewModel = viewModel
            ) {
                TaskListScreen(
                    onNavigateTaskListList = {
                        navController.navigate(YnymPortalScreen.TaskListList.route)
                    },
                    onNavigateTaskAdd = { taskListId ->
                        navController.navigate(YnymPortalScreen.TaskAdd.createRoute(taskListId))
                    },
                    onNavigateTaskEdit = { taskId ->
                        navController.navigate(YnymPortalScreen.TaskEdit.createRoute(taskId))
                    },
                    onOpenDrawer = { scope.launch { drawerState.open() } }
                )
            }
        }
        composable(route = YnymPortalScreen.TaskListList.route) {
            TaskListListScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(route = YnymPortalScreen.TaskAdd.route) {
            NavigationDrawer(
                drawerState = drawerState,
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
                scope = scope,
                viewModel = viewModel
            ) {
                ConfidentialListScreen(
                    onNavigateConfidentialAdd = {
                        navController.navigate(YnymPortalScreen.ConfidentialAdd.route)
                    },
                    onNavigateConfidentialEdit = { confidentialId ->
                        navController.navigate(
                            YnymPortalScreen.ConfidentialEdit.createRoute(confidentialId)
                        )
                    },
                    onOpenDrawer = { scope.launch { drawerState.open() } }
                )
            }
        }
        composable(route = YnymPortalScreen.ConfidentialAdd.route) {
            NavigationDrawer(
                drawerState = drawerState,
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
                scope = scope,
                viewModel = viewModel
            ) {
                CarListScreen(
                    onNavigateCarAdd = {
                        navController.navigate(YnymPortalScreen.CarAdd.route)
                    },
                    onNavigateCarEdit = { carId ->
                        navController.navigate(
                            YnymPortalScreen.CarEdit.createRoute(carId)
                        )
                    },
                    onOpenDrawer = { scope.launch { drawerState.open() } }
                )
            }
        }
        composable(route = YnymPortalScreen.CarAdd.route) {
            NavigationDrawer(
                drawerState = drawerState,
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
                scope = scope,
                viewModel = viewModel
            ) {
                CarEditScreen(
                    onNavigateBack = { navController.navigateUp() }
                )
            }
        }

        composable(route = YnymPortalScreen.RefuelingList.route) {
            NavigationDrawer(
                drawerState = drawerState,
                scope = scope,
                viewModel = viewModel
            ) {
                RefuelingListScreen(
                    onNavigateRefuelingAdd = { carId ->
                        navController.navigate(YnymPortalScreen.RefuelingAdd.createRoute(carId))
                    },
                    onNavigateRefuelingEdit = { refuelingId ->
                        navController.navigate(
                            YnymPortalScreen.RefuelingEdit.createRoute(
                                refuelingId
                            )
                        )
                    },
                    onOpenDrawer = { scope.launch { drawerState.open() } }
                )
            }
        }
        composable(route = YnymPortalScreen.RefuelingAdd.route) {
            NavigationDrawer(
                drawerState = drawerState,
                scope = scope,
                viewModel = viewModel
            ) {
                RefuelingAddScreen(
                    onNavigateBack = { navController.navigateUp() }
                )
            }
        }
        composable(route = YnymPortalScreen.RefuelingEdit.route) {
            NavigationDrawer(
                drawerState = drawerState,
                scope = scope,
                viewModel = viewModel
            ) {
                RefuelingEditScreen(
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