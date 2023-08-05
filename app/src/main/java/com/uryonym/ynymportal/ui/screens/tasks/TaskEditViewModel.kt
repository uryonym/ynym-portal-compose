package com.uryonym.ynymportal.ui.screens.tasks

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uryonym.ynymportal.data.Task
import com.uryonym.ynymportal.data.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import javax.inject.Inject

data class TaskEditUiState(
    val isLoading: Boolean = false,
    val taskId: String = "",
    val task: Task? = null,
    val title: String = "",
    val description: String = "",
    val deadLine: LocalDate? = null,
    val isComplete: Boolean = false,
    val isShowPicker: Boolean = false,
    val isTaskSaved: Boolean = false
)

@HiltViewModel
class TaskEditViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val taskRepository: TaskRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(TaskEditUiState())
    val uiState: StateFlow<TaskEditUiState> = _uiState

    init {
        _uiState.update {
            it.copy(taskId = savedStateHandle["taskId"]!!)
        }

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
            viewModelScope.launch {
                taskRepository.updateTask(
                    id = uiState.value.taskId,
                    title = uiState.value.title,
                    description = uiState.value.description,
                    deadLine = uiState.value.deadLine,
                    isComplete = uiState.value.isComplete
                )
                _uiState.update {
                    it.copy(isTaskSaved = true)
                }
            }
        }
    }

    fun onDelete() {
        viewModelScope.launch {
            taskRepository.deleteTask(uiState.value.taskId)
            _uiState.update {
                it.copy(isTaskSaved = true)
            }
        }
    }

    private fun getTask() {
        viewModelScope.launch {
            taskRepository.getTask(uiState.value.taskId).let { task ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        title = task.title,
                        description = task.description ?: "",
                        deadLine = task.deadLine,
                        isComplete = task.isComplete
                    )
                }
            }
        }
    }

    private fun refreshTask() {
        viewModelScope.launch {
            taskRepository.refreshTask(uiState.value.taskId).let { task ->
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