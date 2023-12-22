package com.uryonym.ynymportal.ui.screens.tasks

import android.util.Log
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
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
    val taskListTitle: TextFieldValue = TextFieldValue(""),
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
            it.copy(
                currentTaskList = taskList,
                taskListTitle = TextFieldValue(
                    taskList.name,
                    selection = TextRange(taskList.name.length)
                )
            )
        }
    }

    fun onChangeTitle(value: TextFieldValue) {
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

    fun onChangeListIndex(from: Int, to: Int) {
        Log.d("debug", "from: $from to: $to")
        val newTaskList = _uiState.value.taskLists.toMutableList().apply {
            add(to, removeAt(from))
        }
        _uiState.update {
            it.copy(taskLists = newTaskList)
        }
    }

    fun onSave() {
        viewModelScope.launch {
            if (uiState.value.taskListTitle.text.isNotEmpty()) {
                if (uiState.value.currentTaskList == null) {
                    // 新規作成処理
                    val taskList = TaskList(
                        name = uiState.value.taskListTitle.text,
                        seq = uiState.value.taskLists.size + 1
                    )
                    taskListRepository.insertTaskList(taskList)
                } else {
                    // 更新処理
                    val updateTaskList = uiState.value.currentTaskList!!.copy(
                        name = uiState.value.taskListTitle.text
                    )
                    taskListRepository.updateTaskList(updateTaskList)
                }
            }
            onChangeShowModal(false)
        }
    }

    fun onDelete() {
        viewModelScope.launch {
            uiState.value.currentTaskList?.let {
                taskListRepository.deleteTaskList(it.id)
            }
            onChangeShowModal(false)
        }
    }

    private fun onClearState() {
        _uiState.update {
            it.copy(currentTaskList = null, taskListTitle = TextFieldValue(""))
        }
    }

}
