package com.uryonym.ynymportal.ui.screens.tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uryonym.ynymportal.data.AuthRepository
import com.uryonym.ynymportal.data.DefaultAuthRepository
import com.uryonym.ynymportal.data.DefaultTaskRepository
import com.uryonym.ynymportal.data.Task
import com.uryonym.ynymportal.data.TaskRepository
import com.uryonym.ynymportal.data.network.YnymPortalApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class TasksUiState(
    val isLoading: Boolean = false,
    val tasks: List<Task> = emptyList()
)

class TaskViewModel : ViewModel() {

    // ViewModelの中でRepositoryのインスタンスを作っているのが依存関係になっている
    // hiltを使って解消すべき部分
    private val taskRepository: TaskRepository = DefaultTaskRepository()
    private val authRepository: AuthRepository = DefaultAuthRepository()

    private val _isLoading = MutableStateFlow(false)

    val uiState: StateFlow<TasksUiState> = combine(
        _isLoading, streamTasks()
    ) { isLoading, tasks ->
        if (tasks.isNotEmpty()) {
            TasksUiState(isLoading = false, tasks = tasks)
        } else {
            TasksUiState(isLoading = true)
        }
    }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = TasksUiState(isLoading = true)
        )

    private fun streamTasks(): Flow<List<Task>> = flow {
        val token = authRepository.getIdToken()
        emit(taskRepository.getTasks(token))
    }

    fun onSaveStatus(currentTask: Task, status: Boolean) {
        viewModelScope.launch {
            val editTask = Task(isComplete = status)

            currentTask.id?.let {
                YnymPortalApi.retrofitService.editTask(id = it, task = editTask, token = "")
            }
        }
    }
}