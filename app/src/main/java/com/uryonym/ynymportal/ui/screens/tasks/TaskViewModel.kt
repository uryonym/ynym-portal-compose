package com.uryonym.ynymportal.ui.screens.tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uryonym.ynymportal.data.DefaultTaskRepository
import com.uryonym.ynymportal.data.Task
import com.uryonym.ynymportal.data.TaskRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class TaskListUiState(
    val tasks: List<Task> = emptyList()
)

class TaskViewModel : ViewModel() {

    // ViewModelの中でRepositoryのインスタンスを作っているのが依存関係になっている
    // hiltを使って解消すべき部分
    private val taskRepository: TaskRepository = DefaultTaskRepository()

    private val _uiState = MutableStateFlow(TaskListUiState())
    val uiState: StateFlow<TaskListUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            taskRepository.getTasks()
                .collect { tasks ->
                    _uiState.update { it.copy(tasks) }
                }
        }
    }

    fun onSaveStatus(task: Task, status: Boolean) {
        viewModelScope.launch {
            task.isComplete = status
            task.id?.let {
                taskRepository.editTask(it, task)
            }
        }
    }

    fun refreshTask() {
        viewModelScope.launch {
            taskRepository.refreshTask()
        }
    }
}