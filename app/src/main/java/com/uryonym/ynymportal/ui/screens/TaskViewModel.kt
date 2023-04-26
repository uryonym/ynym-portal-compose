package com.uryonym.ynymportal.ui.screens

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uryonym.ynymportal.data.Task
import com.uryonym.ynymportal.data.network.YnymPortalApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TaskViewModel : ViewModel() {

    data class TaskUiState(
        val taskList: List<Task>,
        val taskTitle: String
    )

    private val _taskUiState = MutableStateFlow(TaskUiState(taskList = listOf(), taskTitle = ""))
    val taskUiState: StateFlow<TaskUiState> = _taskUiState.asStateFlow()

    init {
        getTasks()
    }

    private fun getTasks() {
        viewModelScope.launch {
            val result = YnymPortalApi.retrofitService.getTasks()
            Log.i("TaskViewModel", result.toString())
            _taskUiState.value = TaskUiState(result, "")
        }
    }
}