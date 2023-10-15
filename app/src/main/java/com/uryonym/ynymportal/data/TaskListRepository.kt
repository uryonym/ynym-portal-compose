package com.uryonym.ynymportal.data

import com.uryonym.ynymportal.data.local.TaskListLocalDataSource
import com.uryonym.ynymportal.data.model.TaskList
import com.uryonym.ynymportal.data.remote.TaskListRemoteDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

interface TaskListRepository {
    fun getTaskLists(): Flow<List<TaskList>>

    suspend fun getTaskList(id: String): TaskList

    suspend fun insertTaskList(taskList: TaskList)

    suspend fun updateTaskList(taskList: TaskList)

    suspend fun refreshTaskLists()
}

@Singleton
class TaskListRepositoryImpl @Inject constructor(
    private val taskListLocalDataSource: TaskListLocalDataSource,
    private val taskListRemoteDataSource: TaskListRemoteDataSource
) : TaskListRepository {
    override fun getTaskLists(): Flow<List<TaskList>> {
        return taskListLocalDataSource.fetchTaskLists()
    }

    override suspend fun getTaskList(id: String): TaskList {
        return taskListLocalDataSource.getTaskList(id)
    }

    override suspend fun insertTaskList(taskList: TaskList) {
        taskListRemoteDataSource.createTaskList(taskList)
        taskListLocalDataSource.upsertTaskList(taskList)
    }

    override suspend fun updateTaskList(taskList: TaskList) {
        taskListRemoteDataSource.updateTaskList(taskList)
        taskListLocalDataSource.upsertTaskList(taskList)
    }

    override suspend fun refreshTaskLists() {
        val localTaskLists = taskListLocalDataSource.getTaskLists()
        val remoteTaskLists = taskListRemoteDataSource.getTaskLists()
        val localIds = localTaskLists.map { it.id }.toSet()
        val remoteIds = remoteTaskLists.map { it.id }.toSet()
        val deleteIds = localIds.subtract(remoteIds).toList()

        taskListLocalDataSource.upsertTaskLists(remoteTaskLists)
        taskListLocalDataSource.deleteTaskListsById(deleteIds)
    }
}
