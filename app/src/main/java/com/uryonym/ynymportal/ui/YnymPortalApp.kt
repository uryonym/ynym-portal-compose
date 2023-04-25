package com.uryonym.ynymportal.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.uryonym.ynymportal.ui.screens.TaskScreen

@Composable
fun YnymPortalApp(modifier: Modifier = Modifier) {
    Scaffold(topBar = {
        TopAppBar(title = { Text(text = "test") })
    }) { innerPadding ->
        TaskScreen(modifier = modifier.padding(innerPadding))
    }
}