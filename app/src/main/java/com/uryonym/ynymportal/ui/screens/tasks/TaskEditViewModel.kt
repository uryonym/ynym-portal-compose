package com.uryonym.ynymportal.ui.screens.tasks

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uryonym.ynymportal.data.AuthRepository
import com.uryonym.ynymportal.data.DefaultAuthRepository
import com.uryonym.ynymportal.data.DefaultTaskRepository
import com.uryonym.ynymportal.data.Task
import com.uryonym.ynymportal.data.TaskRepository
import com.uryonym.ynymportal.data.network.YnymPortalApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate

data class TaskEditUiState(
    val isLoading: Boolean = false,
    val task: Task? = null,
    val title: String = "",
    val description: String = "",
    val deadLine: LocalDate? = null,
    val isComplete: Boolean = false,
    val isShowPicker: Boolean = false,
    val isTaskSaved: Boolean = false
)

class TaskEditViewModel constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    // ViewModelの中でRepositoryのインスタンスを作っているのが依存関係になっている
    // hiltを使って解消すべき部分
    private val taskRepository: TaskRepository = DefaultTaskRepository()
    private val authRepository: AuthRepository = DefaultAuthRepository()

    private val taskId: String = savedStateHandle["taskId"]!!

    private val _uiState = MutableStateFlow(TaskEditUiState())
    val uiState: StateFlow<TaskEditUiState> = _uiState.asStateFlow()

    init {
        getTask()
    }

    fun onChangeTitle(value: String) {
        _uiState.update {
            it.copy(title = value)
        }
    }

    fun onChangeDescription(value: String) {
        _uiState.update {
            it.copy(description = value)
        }
    }

    fun onChangeDeadLine(value: LocalDate) {
        _uiState.update {
            it.copy(deadLine = value)
        }
    }

    fun onChangeShowPicker(value: Boolean) {
        _uiState.update {
            it.copy(isShowPicker = value)
        }
    }

    fun onSaveEditTask() {
        if (uiState.value.title.isNotEmpty()) {
            val editTask = Task(
                title = uiState.value.title,
                description = uiState.value.description,
                deadLine = uiState.value.deadLine,
                isComplete = uiState.value.isComplete
            )
            viewModelScope.launch {
                val token = authRepository.getIdToken()
                taskRepository.editTask(taskId, editTask, token)
                _uiState.update {
                    it.copy(isTaskSaved = true)
                }
            }
        }
    }

    fun onSaveStatus(currentTask: Task, status: Boolean) {
        viewModelScope.launch {
            val editTask = Task(isComplete = status)

            currentTask.id?.let {
                YnymPortalApi.retrofitService.editTask(it, editTask, "")
            }
        }
    }

    fun onDelete() {
        viewModelScope.launch {
            val token = authRepository.getIdToken()
            taskRepository.deleteTask(taskId, token)
        }
    }

    private fun getTask() {
        viewModelScope.launch {
            val token = authRepository.getIdToken()
            taskRepository.getTask(taskId, token).let { task ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        title = task.title,
                        description = task.description ?: "",
                        deadLine = task.deadLine,
                    )
                }
            }
        }
    }
}