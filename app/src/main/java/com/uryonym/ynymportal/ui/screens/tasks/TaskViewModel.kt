package com.uryonym.ynymportal.ui.screens.tasks

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uryonym.ynymportal.data.DefaultTaskRepository
import com.uryonym.ynymportal.data.Task
import com.uryonym.ynymportal.data.TaskRepository
import com.uryonym.ynymportal.data.network.YnymPortalApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate

data class TasksUiState(
    val isLoading: Boolean = false,
    val tasks: List<Task> = emptyList()
)

class TaskViewModel : ViewModel() {

    // ViewModelの中でRepositoryのインスタンスを作っているのが依存関係になっている
    // hiltを使って解消すべき部分
    private val taskRepository: TaskRepository = DefaultTaskRepository()

    private val _isLoading = MutableStateFlow(false)
    private val _tasks = MutableStateFlow<List<Task>>(emptyList())

    val uiState: StateFlow<TasksUiState> = combine(
        _isLoading, _tasks
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

    var currentId: String? by mutableStateOf(null)
        private set

    var title: String by mutableStateOf("")
        private set

    var description: String by mutableStateOf("")
        private set

    var deadLine: LocalDate? by mutableStateOf(null)
        private set

    var showPicker: Boolean by mutableStateOf(false)
        private set

    init {
        getTasks()
    }

    fun onChangeTitle(value: String) {
        title = value
    }

    fun onChangeDescription(value: String) {
        description = value
    }

    fun onChangeDeadLine(value: LocalDate) {
        deadLine = value
    }

    fun onClickTaskItem(task: Task) {
        currentId = task.id
        title = task.title
        description = task.description ?: ""
        deadLine = task.deadLine
    }

    fun onSaveNewTask() {
        viewModelScope.launch {
            val newTask = Task(
                title = title,
                description = description,
                deadLine = deadLine,
                isComplete = false
            )
            onClearState()

            taskRepository.addTask(newTask)
            getTasks()
        }
    }

    fun onSaveEditTask() {
        viewModelScope.launch {
            val editTask = Task(
                title = title,
                description = description,
                deadLine = deadLine,
                isComplete = false
            )
            onClearState()

            currentId?.let {
                taskRepository.editTask(it, editTask)
            }
            getTasks()
        }
    }

    fun onSaveStatus(currentTask: Task, status: Boolean) {
        viewModelScope.launch {
            val editTask = Task(isComplete = status)

            currentTask.id?.let {
                YnymPortalApi.retrofitService.editTask(id = it, task = editTask)
            }
            getTasks()
        }
    }

    fun onDelete() {
        viewModelScope.launch {
            onClearState()

            currentId?.let {
                taskRepository.deleteTask(it)
            }
            getTasks()
        }
    }

    fun onClearState() {
        title = ""
        description = ""
        deadLine = null
    }

    fun onChangePicker(status: Boolean) {
        showPicker = status
    }

    private fun getTasks() {
        viewModelScope.launch {
            taskRepository.getTasks().collect {
                _tasks.value = it
            }
        }
    }
}