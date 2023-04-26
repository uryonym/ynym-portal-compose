package com.uryonym.ynymportal.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.uryonym.ynymportal.ui.screens.AddTaskScreen
import com.uryonym.ynymportal.ui.screens.TaskScreen

@Composable
fun YnymPortalApp(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    Scaffold(topBar = {
        CenterAlignedTopAppBar(title = { Text(text = "タスク") })
    }, floatingActionButton = {
        FloatingActionButton(onClick = { navController.navigate("addTask") }) {
            Icon(Icons.Filled.Add, "タスクの追加")
        }
    }) { innerPadding ->
        NavHost(navController = navController, startDestination = "tasks") {
            composable("tasks") {
                TaskScreen(modifier = modifier.padding(innerPadding))
            }
            composable("addTask") {
                AddTaskScreen(modifier = modifier.padding(innerPadding))
            }
        }
    }
}