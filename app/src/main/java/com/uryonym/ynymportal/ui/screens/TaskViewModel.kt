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

    var task: Task by mutableStateOf(Task())
        private set

    var title: String by mutableStateOf("")
        private set

    var description: String by mutableStateOf("")
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
        this.task = task
        title = task.title
        description = task.description ?: ""
    }

    fun onSaveNewTask() {
        viewModelScope.launch {
            val newTask = Task(title = title, description = description)
            onClearState()

            YnymPortalApi.retrofitService.addTask(task = newTask)
            val result = YnymPortalApi.retrofitService.getTasks()
            _taskList.value = result
        }
    }

    fun onSaveEditTask() {
        viewModelScope.launch {
            task.title = title
            task.description = description
            onClearState()

            task.id?.let {
                YnymPortalApi.retrofitService.editTask(id = it, task = task)
                val result = YnymPortalApi.retrofitService.getTasks()
                _taskList.value = result
            }
        }
    }

    fun onSaveStatus(currentTask: Task) {
        viewModelScope.launch {
            task = currentTask

            task.id?.let {
                YnymPortalApi.retrofitService.editTask(id = it, task = task)
                val result = YnymPortalApi.retrofitService.getTasks()
                _taskList.value = result
            }
        }
    }

    fun onDelete() {
        viewModelScope.launch {
            onClearState()

            task.id?.let {
                YnymPortalApi.retrofitService.deleteTask(it)
                val result = YnymPortalApi.retrofitService.getTasks()
                _taskList.value = result
            }
        }
    }

    fun onClearState() {
        title = ""
        description = ""
    }

    private fun getTasks() {
        viewModelScope.launch {
            val result = YnymPortalApi.retrofitService.getTasks()
            _taskList.value = result
        }
    }
}