package com.uryonym.ynymportal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.StringRes
import androidx.core.view.WindowCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.uryonym.ynymportal.ui.screens.TaskAddScreen
import com.uryonym.ynymportal.ui.screens.TaskEditScreen
import com.uryonym.ynymportal.ui.screens.TaskListScreen
import com.uryonym.ynymportal.ui.screens.TaskViewModel
import com.uryonym.ynymportal.ui.theme.YnymPortalTheme

enum class YnymPortalScreen(@StringRes val title: Int) {
    TaskList(title = R.string.task_list),
    TaskAdd(title = R.string.add_task),
    TaskEdit(title = R.string.edit_task)
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            val navController = rememberNavController()
            val taskViewModel: TaskViewModel = viewModel()

            YnymPortalTheme {
                NavHost(
                    navController = navController,
                    startDestination = YnymPortalScreen.TaskList.name
                ) {
                    composable(route = YnymPortalScreen.TaskList.name) {
                        TaskListScreen(
                            taskViewModel = taskViewModel,
                            onNavigateTaskAdd = { navController.navigate(YnymPortalScreen.TaskAdd.name) },
                            onNavigateTaskEdit = { navController.navigate(YnymPortalScreen.TaskEdit.name) }
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
                }
            }
        }
    }
}
