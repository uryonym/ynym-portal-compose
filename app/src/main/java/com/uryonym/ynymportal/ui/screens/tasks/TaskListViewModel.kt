package com.uryonym.ynymportal.ui.screens.tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uryonym.ynymportal.data.Task
import com.uryonym.ynymportal.data.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class TaskListUiState(
    val isLoading: Boolean = false,
    val tasks: List<Task> = emptyList()
)

@HiltViewModel
class TaskListViewModel @Inject constructor(
    private val taskRepository: TaskRepository
) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)

    private val _uiState = MutableStateFlow(TaskListUiState())
    val uiState: StateFlow<TaskListUiState> = _uiState

    init {
        viewModelScope.launch {
            taskRepository.getTasks().collect { tasks ->
                _uiState.update {
                    it.copy(tasks = tasks)
                }
            }
        }
    }

    fun onSaveStatus(task: Task, status: Boolean) {
//        viewModelScope.launch {
//            task.isComplete = status
//            task.id?.let {
//                taskRepository.updateTask(it, task)
//            }
//        }
    }

    fun refreshTasks() {
        viewModelScope.launch {
            taskRepository.refreshTasks()
        }
    }
}