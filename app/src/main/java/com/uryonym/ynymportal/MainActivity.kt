package com.uryonym.ynymportal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
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
    TaskList(title = R.string.task_list), TaskAdd(title = R.string.add_task), TaskEdit(title = R.string.edit_task)
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
                    composable(route = YnymPortalScreen.TaskList.name) {
                        Scaffold(topBar = {
                            CenterAlignedTopAppBar(title = {
                                Text(stringResource(id = YnymPortalScreen.TaskList.title))
                            })
                        }, bottomBar = {
                            BottomAppBar(actions = {
                                IconButton(onClick = { /*TODO*/ }) {
                                    Icon(
                                        imageVector = Icons.Filled.Menu,
                                        contentDescription = "メニュー"
                                    )
                                }
                            }, floatingActionButton = {
                                FloatingActionButton(onClick = {
                                    navController.navigate(
                                        YnymPortalScreen.TaskAdd.name
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
                                            },
                                            modifier = Modifier.clickable {
                                                taskViewModel.onClickTaskItem(task)
                                                navController.navigate(YnymPortalScreen.TaskEdit.name)
                                            })
                                        Divider()
                                    }
                                }
                            }
                        }
                    }
                    composable(route = YnymPortalScreen.TaskAdd.name) {
                        Scaffold(topBar = {
                            CenterAlignedTopAppBar(title = {
                                Text(stringResource(id = YnymPortalScreen.TaskAdd.title))
                            }, navigationIcon = {
                                IconButton(onClick = { navController.popBackStack() }) {
                                    Icon(
                                        imageVector = Icons.Filled.Close,
                                        contentDescription = "閉じる"
                                    )
                                }
                            }, actions = {
                                TextButton(onClick = {
                                    taskViewModel.onSaveNewTask()
                                    navController.popBackStack()
                                }) {
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
                                OutlinedTextField(
                                    value = taskViewModel.title,
                                    label = { Text("タスク") },
                                    onValueChange = { taskViewModel.onChangeTitle(it) },
                                    modifier = Modifier.fillMaxWidth()
                                )
                                OutlinedTextField(
                                    value = taskViewModel.description,
                                    label = { Text("詳細") },
                                    onValueChange = { taskViewModel.onChangeDescription(it) },
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }
                        }
                    }
                    composable(route = YnymPortalScreen.TaskEdit.name) {
                        Scaffold(topBar = {
                            CenterAlignedTopAppBar(title = {
                                Text(stringResource(id = YnymPortalScreen.TaskEdit.title))
                            }, navigationIcon = {
                                IconButton(onClick = { navController.popBackStack() }) {
                                    Icon(
                                        imageVector = Icons.Filled.ArrowBack,
                                        contentDescription = "戻る"
                                    )
                                }
                            }, actions = {
                                TextButton(onClick = {
                                    taskViewModel.onSaveEditTask()
                                    navController.popBackStack()
                                }) {
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
                                OutlinedTextField(
                                    value = taskViewModel.title,
                                    label = { Text("タスク") },
                                    onValueChange = { taskViewModel.onChangeTitle(it) },
                                    modifier = Modifier.fillMaxWidth()
                                )
                                OutlinedTextField(
                                    value = taskViewModel.description,
                                    label = { Text("詳細") },
                                    onValueChange = { taskViewModel.onChangeDescription(it) },
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
