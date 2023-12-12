package com.uryonym.ynymportal.ui.screens.tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uryonym.ynymportal.data.TaskListRepository
import com.uryonym.ynymportal.data.TaskRepository
import com.uryonym.ynymportal.data.model.Task
import com.uryonym.ynymportal.data.model.TaskList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

data class TaskListUiState(
    val taskLists: List<TaskList> = emptyList(),
    val tasks: List<Task> = emptyList(),
    val selectedTaskListId: String? = null,
)

@HiltViewModel
class TaskListViewModel @Inject constructor(
    private val taskListRepository: TaskListRepository,
    private val taskRepository: TaskRepository
) : ViewModel() {

    private val _selectedTaskListid: MutableStateFlow<String?> = MutableStateFlow(null)

    val uiState: StateFlow<TaskListUiState> = combine(
        taskListRepository.getTaskLists(), taskRepository.getTasks(), _selectedTaskListid
    ) { taskLists, tasks, selectedTaskListId ->
        if (selectedTaskListId != null) {
            TaskListUiState(
                taskLists = taskLists,
                tasks = tasks,
                selectedTaskListId = selectedTaskListId
            )
        } else {
            TaskListUiState(
                taskLists = taskLists,
                tasks = tasks,
                selectedTaskListId = if (taskLists.isNotEmpty()) taskLists.first().id else null
            )
        }
    }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = TaskListUiState()
        )

    fun onClickTaskListTab(index: Int) {
        val taskListId = uiState.value.taskLists[index].id
        _selectedTaskListid.value = taskListId
    }

    fun onSaveStatus(task: Task, status: Boolean) {
        val updateTask = task.copy(isComplete = status)
        viewModelScope.launch {
            taskRepository.updateTask(updateTask)
        }
    }

    fun refresh() {
        viewModelScope.launch {
            taskListRepository.refreshTaskLists()
            taskRepository.refreshTasks()
        }
    }

}
