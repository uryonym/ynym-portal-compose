package com.uryonym.ynymportal.ui.screens.tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uryonym.ynymportal.data.TaskListRepository
import com.uryonym.ynymportal.data.model.TaskList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class TaskListListUiState(
    val taskLists: List<TaskList> = emptyList(),
    val currentTaskList: TaskList? = null,
    val taskListTitle: String = "",
    val isShowModal: Boolean = false
)

@HiltViewModel
class TaskListListViewModel @Inject constructor(
    private val taskListRepository: TaskListRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(TaskListListUiState())
    val uiState: StateFlow<TaskListListUiState> = _uiState

    init {
        viewModelScope.launch {
            taskListRepository.getTaskLists().collect { taskLists ->
                _uiState.update {
                    it.copy(taskLists = taskLists)
                }
            }
        }
    }

    fun setTaskList(taskList: TaskList) {
        _uiState.update {
            it.copy(currentTaskList = taskList, taskListTitle = taskList.name)
        }
    }

    fun onChangeTitle(value: String) {
        _uiState.update {
            it.copy(taskListTitle = value)
        }
    }

    fun onChangeShowModal(value: Boolean) {
        _uiState.update {
            it.copy(isShowModal = value)
        }
        if (!value) {
            onClearState()
        }
    }

    fun onSave() {
        viewModelScope.launch {
            if (uiState.value.taskListTitle.isNotEmpty()) {
                if (uiState.value.currentTaskList == null) {
                    // 新規作成処理
                    val taskList = TaskList(
                        name = uiState.value.taskListTitle,
                        seq = uiState.value.taskLists.size + 1
                    )
                    taskListRepository.insertTaskList(taskList)
                } else {
                    // 更新処理
                    val updateTaskList = uiState.value.currentTaskList!!.copy(
                        name = uiState.value.taskListTitle
                    )
                    taskListRepository.updateTaskList(updateTaskList)
                }
            }
            onChangeShowModal(false)
        }
    }

    private fun onClearState() {
        _uiState.update {
            it.copy(currentTaskList = null, taskListTitle = "")
        }
    }
}
