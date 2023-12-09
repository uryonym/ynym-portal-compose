package com.uryonym.ynymportal.ui

import androidx.activity.compose.BackHandler
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.uryonym.ynymportal.navigation.YnymPortalScreen.SignInScreen
import com.uryonym.ynymportal.navigation.YnymPortalScreen.TaskListScreen
import com.uryonym.ynymportal.navigation.YnymPortalScreen.TaskListListScreen
import com.uryonym.ynymportal.navigation.YnymPortalScreen.TaskAddScreen
import com.uryonym.ynymportal.navigation.YnymPortalScreen.TaskEditScreen
import com.uryonym.ynymportal.navigation.YnymPortalScreen.ConfidentialListScreen
import com.uryonym.ynymportal.navigation.YnymPortalScreen.ConfidentialAddScreen
import com.uryonym.ynymportal.navigation.YnymPortalScreen.ConfidentialEditScreen
import com.uryonym.ynymportal.navigation.YnymPortalScreen.CarListScreen
import com.uryonym.ynymportal.navigation.YnymPortalScreen.CarAddScreen
import com.uryonym.ynymportal.navigation.YnymPortalScreen.CarEditScreen
import com.uryonym.ynymportal.navigation.YnymPortalScreen.RefuelingListScreen
import com.uryonym.ynymportal.navigation.YnymPortalScreen.RefuelingAddScreen
import com.uryonym.ynymportal.navigation.YnymPortalScreen.RefuelingEditScreen
import com.uryonym.ynymportal.ui.screens.cars.CarAddScreen
import com.uryonym.ynymportal.ui.screens.cars.CarEditScreen
import com.uryonym.ynymportal.ui.screens.cars.CarListScreen
import com.uryonym.ynymportal.ui.screens.confidentials.ConfidentialAddScreen
import com.uryonym.ynymportal.ui.screens.confidentials.ConfidentialEditScreen
import com.uryonym.ynymportal.ui.screens.confidentials.ConfidentialListScreen
import com.uryonym.ynymportal.ui.screens.refuelings.RefuelingAddScreen
import com.uryonym.ynymportal.ui.screens.refuelings.RefuelingEditScreen
import com.uryonym.ynymportal.ui.screens.refuelings.RefuelingListScreen
import com.uryonym.ynymportal.ui.screens.sign_in.SignInScreen
import com.uryonym.ynymportal.ui.screens.tasks.TaskAddScreen
import com.uryonym.ynymportal.ui.screens.tasks.TaskEditScreen
import com.uryonym.ynymportal.ui.screens.tasks.TaskListListScreen
import com.uryonym.ynymportal.ui.screens.tasks.TaskListScreen
import kotlinx.coroutines.launch

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

    val isUserSignedOut = viewModel.getAuthState().collectAsState().value
    NavHost(
        navController = navController,
        startDestination = if (isUserSignedOut) SignInScreen.route else TaskListScreen.route
    ) {
        composable(route = SignInScreen.route) {
            SignInScreen()
        }

        composable(route = TaskListScreen.route) {
            NavigationDrawer(
                drawerState = drawerState,
                scope = scope,
                navigate = { route -> navController.navigate(route) },
                onSignOut = viewModel::signOut
            ) {
                TaskListScreen(
                    onNavigateTaskListList = {
                        navController.navigate(TaskListListScreen.route)
                    },
                    onNavigateTaskAdd = { taskListId ->
                        navController.navigate(TaskAddScreen.createRoute(taskListId))
                    },
                    onNavigateTaskEdit = { taskId ->
                        navController.navigate(TaskEditScreen.createRoute(taskId))
                    },
                    onOpenDrawer = { scope.launch { drawerState.open() } }
                )
            }
        }
        composable(route = TaskListListScreen.route) {
            TaskListListScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(route = TaskAddScreen.route) {
            NavigationDrawer(
                drawerState = drawerState,
                scope = scope,
                navigate = { route -> navController.navigate(route) },
                onSignOut = viewModel::signOut
            ) {
                TaskAddScreen(
                    onNavigateBack = { navController.popBackStack() }
                )
            }
        }
        composable(route = TaskEditScreen.route) {
            NavigationDrawer(
                drawerState = drawerState,
                scope = scope,
                navigate = { route -> navController.navigate(route) },
                onSignOut = viewModel::signOut
            ) {
                TaskEditScreen(
                    onNavigateBack = { navController.popBackStack() }
                )
            }
        }

        composable(route = ConfidentialListScreen.route) {
            NavigationDrawer(
                drawerState = drawerState,
                scope = scope,
                navigate = { route -> navController.navigate(route) },
                onSignOut = viewModel::signOut
            ) {
                ConfidentialListScreen(
                    onNavigateConfidentialAdd = {
                        navController.navigate(ConfidentialAddScreen.route)
                    },
                    onNavigateConfidentialEdit = { confidentialId ->
                        navController.navigate(
                            ConfidentialEditScreen.createRoute(confidentialId)
                        )
                    },
                    onOpenDrawer = { scope.launch { drawerState.open() } }
                )
            }
        }
        composable(route = ConfidentialAddScreen.route) {
            NavigationDrawer(
                drawerState = drawerState,
                scope = scope,
                navigate = { route -> navController.navigate(route) },
                onSignOut = viewModel::signOut
            ) {
                ConfidentialAddScreen(
                    onNavigateBack = { navController.popBackStack() }
                )
            }
        }
        composable(route = ConfidentialEditScreen.route) {
            NavigationDrawer(
                drawerState = drawerState,
                scope = scope,
                navigate = { route -> navController.navigate(route) },
                onSignOut = viewModel::signOut
            ) {
                ConfidentialEditScreen(
                    onNavigateBack = { navController.popBackStack() }
                )
            }
        }

        composable(route = CarListScreen.route) {
            NavigationDrawer(
                drawerState = drawerState,
                scope = scope,
                navigate = { route -> navController.navigate(route) },
                onSignOut = viewModel::signOut
            ) {
                CarListScreen(
                    onNavigateCarAdd = {
                        navController.navigate(CarAddScreen.route)
                    },
                    onNavigateCarEdit = { carId ->
                        navController.navigate(
                            CarEditScreen.createRoute(carId)
                        )
                    },
                    onOpenDrawer = { scope.launch { drawerState.open() } }
                )
            }
        }
        composable(route = CarAddScreen.route) {
            NavigationDrawer(
                drawerState = drawerState,
                scope = scope,
                navigate = { route -> navController.navigate(route) },
                onSignOut = viewModel::signOut
            ) {
                CarAddScreen(
                    onNavigateBack = { navController.navigateUp() }
                )
            }
        }
        composable(route = CarEditScreen.route) {
            NavigationDrawer(
                drawerState = drawerState,
                scope = scope,
                navigate = { route -> navController.navigate(route) },
                onSignOut = viewModel::signOut
            ) {
                CarEditScreen(
                    onNavigateBack = { navController.navigateUp() }
                )
            }
        }

        composable(route = RefuelingListScreen.route) {
            NavigationDrawer(
                drawerState = drawerState,
                scope = scope,
                navigate = { route -> navController.navigate(route) },
                onSignOut = viewModel::signOut
            ) {
                RefuelingListScreen(
                    onNavigateRefuelingAdd = { carId ->
                        navController.navigate(RefuelingAddScreen.createRoute(carId))
                    },
                    onNavigateRefuelingEdit = { refuelingId ->
                        navController.navigate(
                            RefuelingEditScreen.createRoute(
                                refuelingId
                            )
                        )
                    },
                    onOpenDrawer = { scope.launch { drawerState.open() } }
                )
            }
        }
        composable(route = RefuelingAddScreen.route) {
            NavigationDrawer(
                drawerState = drawerState,
                scope = scope,
                navigate = { route -> navController.navigate(route) },
                onSignOut = viewModel::signOut
            ) {
                RefuelingAddScreen(
                    onNavigateBack = { navController.navigateUp() }
                )
            }
        }
        composable(route = RefuelingEditScreen.route) {
            NavigationDrawer(
                drawerState = drawerState,
                scope = scope,
                navigate = { route -> navController.navigate(route) },
                onSignOut = viewModel::signOut
            ) {
                RefuelingEditScreen(
                    onNavigateBack = { navController.navigateUp() }
                )
            }
        }
    }
}
