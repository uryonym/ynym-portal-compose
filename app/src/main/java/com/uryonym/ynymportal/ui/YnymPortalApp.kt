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
import com.uryonym.ynymportal.ui.screens.AddTaskScreen
import com.uryonym.ynymportal.ui.screens.TaskScreen

@Composable
fun YnymPortalApp(modifier: Modifier = Modifier) {
    Scaffold(topBar = {
        CenterAlignedTopAppBar(title = { Text(text = "タスク") })
    }, floatingActionButton = {
        FloatingActionButton(onClick = { /*TODO*/ }) {
            Icon(Icons.Filled.Add, "タスクの追加")
        }
    }) { innerPadding ->
        TaskScreen(modifier = modifier.padding(innerPadding))
//        AddTaskScreen(modifier = modifier.padding(innerPadding))
    }
}