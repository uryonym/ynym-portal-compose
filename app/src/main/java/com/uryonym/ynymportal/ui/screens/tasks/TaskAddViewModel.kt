package com.uryonym.ynymportal.ui.screens.tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uryonym.ynymportal.data.DefaultTaskRepository
import com.uryonym.ynymportal.data.Task
import com.uryonym.ynymportal.data.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import java.util.UUID
import javax.inject.Inject

data class TaskAddUiState(
    val isLoading: Boolean = false,
    val task: Task? = null,
    val title: String = "",
    val description: String = "",
    val deadLine: LocalDate? = null,
    val isComplete: Boolean = false,
    val isShowPicker: Boolean = false,
    val isTaskSaved: Boolean = false
)

@HiltViewModel
class TaskAddViewModel @Inject constructor(
    private val taskRepository: TaskRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(TaskAddUiState())
    val uiState: StateFlow<TaskAddUiState> = _uiState.asStateFlow()

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

    fun onSaveNewTask() {
        if (uiState.value.title.isNotEmpty()) {
            val newTask = Task(
                id = UUID.randomUUID().toString(),
                title = uiState.value.title,
                description = uiState.value.description,
                deadLine = uiState.value.deadLine,
                isComplete = uiState.value.isComplete
            )

            viewModelScope.launch {
                taskRepository.addTask(newTask)
                _uiState.update {
                    it.copy(isTaskSaved = true)
                }
            }
        }
    }
}
