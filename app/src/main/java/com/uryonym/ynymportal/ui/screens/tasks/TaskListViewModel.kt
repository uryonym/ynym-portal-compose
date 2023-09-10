package com.uryonym.ynymportal.ui.screens.tasks

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uryonym.ynymportal.data.TaskRepository
import com.uryonym.ynymportal.data.model.Task
import com.uryonym.ynymportal.data.model.TaskList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class TaskListUiState(
    val isLoading: Boolean = false,
    val taskLists: List<TaskList> = emptyList(),
    val tasks: List<Task> = emptyList(),
    val selectedTaskListIndex: Int? = null
)

@HiltViewModel
class TaskListViewModel @Inject constructor(
    private val taskRepository: TaskRepository
) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)

    private val _currentTaskListId = MutableStateFlow<String?>(null)
    val currentTaskListId: StateFlow<String?> = _currentTaskListId

    private val _uiState = MutableStateFlow(TaskListUiState())
    val uiState: StateFlow<TaskListUiState> = combine(
        taskRepository.getTasks(), _currentTaskListId
    ) { tasks, taskListId ->
        if (tasks.isNotEmpty() && taskListId != null) {
            val filteredTasks = tasks.filter { it.taskListId == taskListId }
            val selectedIndex =
                _uiState.value.taskLists.indexOfFirst { it.id == taskListId }
            _uiState.value.copy(tasks = filteredTasks, selectedTaskListIndex = selectedIndex)
        } else {
            _uiState.value.copy(tasks = emptyList(), selectedTaskListIndex = null)
        }
    }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = TaskListUiState()
        )


    init {
        viewModelScope.launch {
            taskRepository.getTaskLists().collect { taskLists ->
                _uiState.update {
                    it.copy(taskLists = taskLists)
                }
                if (taskLists.isNotEmpty()) {
                    _currentTaskListId.value = taskLists.first().id
                }
            }
        }
    }

    fun onClickTaskListTab(index: Int) {
        _currentTaskListId.value = uiState.value.taskLists[index].id
    }

    fun onSaveStatus(taskId: String, status: Boolean) {
        viewModelScope.launch {
            taskRepository.changeStatus(taskId = taskId, status = status)
        }
    }

    fun refreshTasks() {
        viewModelScope.launch {
            taskRepository.refreshTaskLists()
            taskRepository.refreshTasks()
        }
    }
}
