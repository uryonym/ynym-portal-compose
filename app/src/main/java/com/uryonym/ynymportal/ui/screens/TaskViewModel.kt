package com.uryonym.ynymportal.ui.screens

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uryonym.ynymportal.data.DefaultTaskRepository
import com.uryonym.ynymportal.data.Task
import com.uryonym.ynymportal.data.TaskRepository
import com.uryonym.ynymportal.data.network.YnymPortalApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate

class TaskViewModel : ViewModel() {

    private val taskRepository: TaskRepository = DefaultTaskRepository()

    private val _taskList = MutableStateFlow<List<Task>>(listOf())
    val taskList = _taskList.asStateFlow()

    var currentId: String? by mutableStateOf(null)
        private set

    var title: String by mutableStateOf("")
        private set

    var description: String by mutableStateOf("")
        private set

    var deadLine: LocalDate? by mutableStateOf(null)
        private set

    var showPicker: Boolean by mutableStateOf(false)
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

    fun onChangeDeadLine(value: LocalDate) {
        deadLine = value
    }

    fun onClickTaskItem(task: Task) {
        currentId = task.id
        title = task.title
        description = task.description ?: ""
        deadLine = task.deadLine
    }

    fun onSaveNewTask() {
        viewModelScope.launch {
            val newTask = Task(
                title = title,
                description = description,
                deadLine = deadLine,
                isComplete = false
            )
            onClearState()

            taskRepository.addTask(newTask)
            getTasks()
        }
    }

    fun onSaveEditTask() {
        viewModelScope.launch {
            val editTask = Task(
                title = title,
                description = description,
                deadLine = deadLine,
                isComplete = false
            )
            onClearState()

            currentId?.let {
                taskRepository.editTask(it, editTask)
            }
            getTasks()
        }
    }

    fun onSaveStatus(currentTask: Task, status: Boolean) {
        viewModelScope.launch {
            val editTask = Task(isComplete = status)

            currentTask.id?.let {
                YnymPortalApi.retrofitService.editTask(id = it, task = editTask)
            }
            getTasks()
        }
    }

    fun onDelete() {
        viewModelScope.launch {
            onClearState()

            currentId?.let {
                taskRepository.deleteTask(it)
            }
            getTasks()
        }
    }

    fun onClearState() {
        title = ""
        description = ""
        deadLine = null
    }

    fun onChangePicker(status: Boolean) {
        showPicker = status
    }

    private fun getTasks() {
        viewModelScope.launch {
            taskRepository.getTasks().collect {
                _taskList.value = it
            }
        }
    }
}