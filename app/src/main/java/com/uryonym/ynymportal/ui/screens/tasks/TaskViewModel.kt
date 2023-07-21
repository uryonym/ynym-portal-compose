package com.uryonym.ynymportal.ui.screens.tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uryonym.ynymportal.data.DefaultTaskRepository
import com.uryonym.ynymportal.data.Task
import com.uryonym.ynymportal.data.TaskRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class TasksUiState(
    val tasks: List<Task> = emptyList()
)

class TaskViewModel : ViewModel() {

    // ViewModelの中でRepositoryのインスタンスを作っているのが依存関係になっている
    // hiltを使って解消すべき部分
    private val taskRepository: TaskRepository = DefaultTaskRepository()

    val uiState: StateFlow<TasksUiState> = taskRepository.getTasks()
        .map { TasksUiState(tasks = it) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = TasksUiState()
        )

    fun onSaveStatus(task: Task, status: Boolean) {
        viewModelScope.launch {
            task.isComplete = status
            task.id?.let {
                taskRepository.editTask(it, task)
            }
        }
    }
}