package com.uryonym.ynymportal.ui.screens.tasks

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uryonym.ynymportal.data.TaskRepository
import com.uryonym.ynymportal.data.model.Task
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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
    savedStateHandle: SavedStateHandle,
    private val taskRepository: TaskRepository
) : ViewModel() {

    private val taskListId: String = savedStateHandle["taskListId"]!!

    private val _uiState = MutableStateFlow(TaskAddUiState())
    val uiState: StateFlow<TaskAddUiState> = _uiState

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

    fun onChangeDeadLine(value: LocalDate?) {
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
//        viewModelScope.launch {
//            if (uiState.value.title.isNotEmpty()) {
//                val task = Task(
//                    id = UUID.randomUUID().toString(),
//                    title = uiState.value.title,
//                    description = uiState.value.description,
//                    deadLine = uiState.value.deadLine,
//                    isComplete = false,
//                    taskListId = taskListId
//                )
//                taskRepository.insertTask(task)
//                _uiState.update {
//                    it.copy(isTaskSaved = true)
//                }
//            }
//        }
    }
}
