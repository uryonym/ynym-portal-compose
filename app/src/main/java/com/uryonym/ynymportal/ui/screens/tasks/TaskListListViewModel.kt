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
    val isShowAddModal: Boolean = false,
    val isShowEditModal: Boolean = false
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

    fun onChangeShowAddModal(value: Boolean) {
        _uiState.update {
            it.copy(isShowAddModal = value)
        }
    }

    fun onChangeShowEditModal(value: Boolean) {
        _uiState.update {
            it.copy(isShowEditModal = value)
        }
    }
}
