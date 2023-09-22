package com.uryonym.ynymportal.data.local

import com.uryonym.ynymportal.data.model.TaskList
import com.uryonym.ynymportal.data.model.toLocal
import com.uryonym.ynymportal.data.model.toModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

interface TaskListLocalDataSource {
    fun fetchTaskLists(): Flow<List<TaskList>>

    suspend fun getTaskLists(): List<TaskList>

    suspend fun getTaskList(id: String): TaskList

    suspend fun upsertTaskLists(taskLists: List<TaskList>)

    suspend fun deleteTaskListsById(ids: List<String>)
}

@Singleton
class TaskListLocalDataSourceImpl @Inject constructor(
    private val taskListDao: TaskListDao
) : TaskListLocalDataSource {
    override fun fetchTaskLists(): Flow<List<TaskList>> {
        return taskListDao.fetchTaskLists().map { it.toModel() }
    }

    override suspend fun getTaskLists(): List<TaskList> {
        return taskListDao.getTaskLists().toModel()
    }

    override suspend fun getTaskList(id: String): TaskList {
        return taskListDao.getTaskList(id).toModel()
    }

    override suspend fun upsertTaskLists(taskLists: List<TaskList>) {
        taskListDao.upsertTaskLists(taskLists.toLocal())
    }

    override suspend fun deleteTaskListsById(ids: List<String>) {
        ids.forEach { taskListDao.deleteTaskListById(it) }
    }
}
