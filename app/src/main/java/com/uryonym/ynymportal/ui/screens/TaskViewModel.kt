package com.uryonym.ynymportal.ui.screens

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uryonym.ynymportal.data.network.YnymPortalApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class TaskUiState(
    var taskSize: Int = 0
)

class TaskViewModel : ViewModel() {
    private val _taskUiState = MutableStateFlow(TaskUiState())
    val taskUiState: StateFlow<TaskUiState> = _taskUiState.asStateFlow()

    init {
        getTasks()
    }

    private fun getTasks() {
        viewModelScope.launch {
            val result = YnymPortalApi.retrofitService.getTasks()
            Log.i("TaskViewModel", result.toString())
            _taskUiState.value = TaskUiState(taskSize = result.size)
        }
    }
}