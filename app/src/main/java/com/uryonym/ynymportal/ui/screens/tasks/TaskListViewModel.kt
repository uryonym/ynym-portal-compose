package com.uryonym.ynymportal.ui.screens.tasks

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
import javax.inject.Inject

data class TaskListUiState(
    val isLoading: Boolean = false,
    val taskLists: List<TaskList> = emptyList(),
    val tasks: List<Task> = emptyList(),
    val selectedTaskListId: String? = null,
)

@HiltViewModel
class TaskListViewModel @Inject constructor(
    private val taskListRepository: TaskListRepository,
    private val taskRepository: TaskRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(TaskListUiState())
    val uiState: StateFlow<TaskListUiState> = _uiState

    init {
        viewModelScope.launch {
            taskListRepository.getTaskLists().collect { taskLists ->
                _uiState.update {
                    it.copy(
                        taskLists = taskLists,
                        selectedTaskListId = if (taskLists.isNotEmpty()) taskLists.first().id else null
                    )
                }
            }

//            refreshTasks()
        }
    }

    fun onClickTaskListTab(index: Int) {
//        val taskListId = uiState.value.taskLists[index].id
//
//        _uiState.update {
//            it.copy(selectedTaskListId = taskListId)
//        }
//
//        refreshTasks()
    }

//    fun onSaveStatus(task: Task, status: Boolean) {
//        val updateTask = task.copy(isComplete = status)
//        viewModelScope.launch {
//            uiState.value.selectedTaskListId?.let {
//                taskRepository.updateTask(it, updateTask)
//            }
//
//            refreshTasks()
//        }
//    }

    fun refreshTaskLists() {
        viewModelScope.launch {
            taskListRepository.refreshTaskLists()
        }
    }

//    fun refreshTasks() {
//        viewModelScope.launch {
//            uiState.value.selectedTaskListId?.let {
//                taskRepository.getTasks(it).collect { tasks ->
//                    _uiState.update {
//                        it.copy(
//                            tasks = tasks
//                        )
//                    }
//                }
//            }
//        }
//    }

}
