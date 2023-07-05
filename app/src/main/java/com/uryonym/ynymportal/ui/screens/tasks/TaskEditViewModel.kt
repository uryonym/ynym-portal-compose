package com.uryonym.ynymportal.ui.screens.tasks

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
    val isShowPicker: Boolean = false
)

class TaskEditViewModel constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    // ViewModelの中でRepositoryのインスタンスを作っているのが依存関係になっている
    // hiltを使って解消すべき部分
    private val taskRepository: TaskRepository = DefaultTaskRepository()

    private val taskId: String = savedStateHandle["taskId"]!!

    private val _isLoading = MutableStateFlow(false)
    private val _task = MutableStateFlow<Task?>(null)

    private val _uiState = MutableStateFlow(TaskEditUiState())
    val uiState: StateFlow<TaskEditUiState> = _uiState.asStateFlow()

    var currentId: String? by mutableStateOf(null)
        private set

    var title: String by mutableStateOf("")
        private set

    var description: String by mutableStateOf("")
        private set

    var deadLine: LocalDate? by mutableStateOf(null)
        private set

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

    fun onSaveNewTask() {
        viewModelScope.launch {
            val newTask = Task(
                title = title, description = description, deadLine = deadLine, isComplete = false
            )
            onClearState()

            taskRepository.addTask(newTask)
        }
    }

    fun onSaveEditTask() {
        viewModelScope.launch {
            val editTask = Task(
                title = title, description = description, deadLine = deadLine, isComplete = false
            )
            onClearState()

            currentId?.let {
                taskRepository.editTask(it, editTask)
            }
        }
    }

    fun onSaveStatus(currentTask: Task, status: Boolean) {
        viewModelScope.launch {
            val editTask = Task(isComplete = status)

            currentTask.id?.let {
                YnymPortalApi.retrofitService.editTask(id = it, task = editTask)
            }
        }
    }

    fun onDelete() {
        viewModelScope.launch {
            onClearState()

            currentId?.let {
                taskRepository.deleteTask(it)
            }
        }
    }

    fun onClearState() {
        title = ""
        description = ""
        deadLine = null
    }

    private fun getTask() {
        viewModelScope.launch {
            taskRepository.getTask(taskId).let { task ->
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