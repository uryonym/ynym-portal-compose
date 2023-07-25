package com.uryonym.ynymportal.ui.screens.tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uryonym.ynymportal.data.DefaultTaskRepository
import com.uryonym.ynymportal.data.Task
import com.uryonym.ynymportal.data.TaskRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class TaskListUiState(
    val isLoading: Boolean = false,
    val tasks: List<Task> = emptyList()
)

class TaskViewModel : ViewModel() {

    // ViewModelの中でRepositoryのインスタンスを作っているのが依存関係になっている
    // hiltを使って解消すべき部分
    private val taskRepository: TaskRepository = DefaultTaskRepository()

    private val _isLoading = MutableStateFlow(false)
    val uiState: StateFlow<TaskListUiState> =
        combine(
            _isLoading, taskRepository.getTasks()
        ) { isLoading, tasks ->
            if (tasks.isNotEmpty()) {
                TaskListUiState(isLoading = isLoading, tasks = tasks)
            } else {
                TaskListUiState(isLoading = isLoading, tasks = emptyList())
            }
        }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(),
                initialValue = TaskListUiState()
            )

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