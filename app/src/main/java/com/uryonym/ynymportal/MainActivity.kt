package com.uryonym.ynymportal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.core.view.WindowCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.uryonym.ynymportal.ui.screens.TaskViewModel
import com.uryonym.ynymportal.ui.theme.YnymPortalTheme

enum class YnymPortalScreen(@StringRes val title: Int) {
    TaskList(title = R.string.task_list), AddTask(title = R.string.add_task), EditTask(title = R.string.edit_task)
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
                    navController = navController, startDestination = YnymPortalScreen.TaskList.name
                ) {
                    composable(YnymPortalScreen.TaskList.name) {
                        Scaffold(topBar = {
                            CenterAlignedTopAppBar(title = {
                                Text(stringResource(id = YnymPortalScreen.TaskList.title))
                            })
                        }, bottomBar = {
                            BottomAppBar(actions = {
                                IconButton(onClick = { /*TODO*/ }) {
                                    Icon(
                                        imageVector = Icons.Filled.Check,
                                        contentDescription = "完了"
                                    )
                                }
                            }, floatingActionButton = {
                                FloatingActionButton(onClick = {
                                    navController.navigate(
                                        YnymPortalScreen.AddTask.name
                                    )
                                }) {
                                    Icon(
                                        imageVector = Icons.Filled.Add, contentDescription = "追加"
                                    )
                                }
                            })
                        }) { padding ->
                            val taskList by taskViewModel.taskList.collectAsState()

                            LazyColumn(modifier = Modifier.padding(padding)) {
                                items(taskList) { task ->
                                    Column {
                                        ListItem(headlineContent = { Text(text = task.title) },
                                            leadingContent = {
                                                Checkbox(checked = false, onCheckedChange = {})
                                            })
                                        Divider()
                                    }
                                }
                            }
                        }
                    }
                    composable(YnymPortalScreen.AddTask.name) {
                        Scaffold(topBar = {
                            CenterAlignedTopAppBar(title = {
                                Text(stringResource(id = YnymPortalScreen.AddTask.title))
                            }, navigationIcon = {
                                IconButton(onClick = { navController.popBackStack() }) {
                                    Icon(
                                        imageVector = Icons.Filled.Close,
                                        contentDescription = "閉じる"
                                    )
                                }
                            }, actions = {
                                TextButton(onClick = { /*TODO*/ }) {
                                    Text(text = "保存")
                                }
                            })
                        }) { padding ->
                            Column(
                                modifier = Modifier
                                    .padding(padding)
                                    .fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                OutlinedTextField(value = "",
                                    label = { Text("タスク") },
                                    onValueChange = {})
                                OutlinedTextField(value = "",
                                    label = { Text("詳細") },
                                    onValueChange = {})
                            }
                        }
                    }
                }
            }
        }
    }
}
