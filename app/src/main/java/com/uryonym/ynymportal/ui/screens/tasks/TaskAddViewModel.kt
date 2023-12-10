package com.uryonym.ynymportal.ui.screens.tasks

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uryonym.ynymportal.data.TaskListRepository
import com.uryonym.ynymportal.data.TaskRepository
import com.uryonym.ynymportal.data.model.Task
import com.uryonym.ynymportal.data.model.TaskList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import javax.inject.Inject

data class TaskAddUiState(
    val taskList: TaskList? = null,
    val title: TextFieldValue = TextFieldValue(""),
    val description: String = "",
    val deadLine: LocalDate? = null,
    val isComplete: Boolean = false,
    val isShowPicker: Boolean = false,
    val isTaskSaved: Boolean = false
)

@HiltViewModel
class TaskAddViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val taskListRepository: TaskListRepository,
    private val taskRepository: TaskRepository
) : ViewModel() {

    private val taskListId: String = savedStateHandle["taskListId"]!!

    private val _uiState = MutableStateFlow(TaskAddUiState())
    val uiState: StateFlow<TaskAddUiState> = _uiState

    init {
        viewModelScope.launch {
            val taskList = taskListRepository.getTaskList(taskListId)
            _uiState.update {
                it.copy(taskList = taskList)
            }
        }
    }

    fun onChangeTitle(value: TextFieldValue) {
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
        viewModelScope.launch {
            if (uiState.value.title.text.isNotEmpty()) {
                val task = Task(
                    title = uiState.value.title.text,
                    description = uiState.value.description,
                    deadLine = uiState.value.deadLine,
                    isComplete = false,
                    taskListId = taskListId
                )
                taskRepository.insertTask(task)

                _uiState.update {
                    it.copy(isTaskSaved = true)
                }
            }
        }
    }

}
