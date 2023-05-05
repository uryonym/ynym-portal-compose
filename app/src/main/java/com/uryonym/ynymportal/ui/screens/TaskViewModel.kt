package com.uryonym.ynymportal.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uryonym.ynymportal.data.Task
import com.uryonym.ynymportal.data.network.YnymPortalApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TaskViewModel : ViewModel() {

    private val _taskList = MutableStateFlow<List<Task>>(listOf())
    val taskList = _taskList.asStateFlow()

    var title by mutableStateOf("")
        private set

    var description by mutableStateOf("")
        private set

    init {
        getTasks()
    }

    fun onChangeTitle(value: String) {
        title = value
    }

    fun onChangeDescription(value: String) {
        description = value
    }

    fun onClickTaskItem(task: Task) {
        title = task.title
        description = task.description ?: ""
    }

    fun onSave() {
        viewModelScope.launch {
            val newTask = Task(title = title, description = description)
            YnymPortalApi.retrofitService.addTask(task = newTask)
            val result = YnymPortalApi.retrofitService.getTasks()
            _taskList.value = result
            title = ""
            description = ""
        }
    }

    private fun getTasks() {
        viewModelScope.launch {
            val result = YnymPortalApi.retrofitService.getTasks()
            _taskList.value = result
        }
    }
}