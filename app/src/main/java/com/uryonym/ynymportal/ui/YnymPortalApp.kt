package com.uryonym.ynymportal.ui

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.uryonym.ynymportal.R
import com.uryonym.ynymportal.ui.screens.AddTaskScreen
import com.uryonym.ynymportal.ui.screens.TaskScreen

enum class YnymPortalScreen(@StringRes val title: Int) {
    TaskList(title = R.string.task_list), AddTask(title = R.string.add_task), EditTask(title = R.string.edit_task)
}

@Composable
fun YnymPortalApp(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    NavHost(
        navController = navController, startDestination = YnymPortalScreen.TaskList.name
    ) {
        composable(YnymPortalScreen.TaskList.name) {
            TaskScreen(navController = navController)
        }
        composable(YnymPortalScreen.AddTask.name) {
            AddTaskScreen(navController = navController)
        }
    }
}