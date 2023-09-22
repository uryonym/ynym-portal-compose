package com.uryonym.ynymportal.data

import com.uryonym.ynymportal.data.local.TaskLocalDataSource
import com.uryonym.ynymportal.data.model.Task
import com.uryonym.ynymportal.data.remote.TaskRemoteDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

interface TaskRepository {
    fun getTasks(): Flow<List<Task>>

    suspend fun getTask(id: String): Task

    suspend fun insertTask(task: Task)

    suspend fun updateTask(task: Task)

    suspend fun deleteTask(id: String)

    suspend fun refreshTasks()
}

@Singleton
class TaskRepositoryImpl @Inject constructor(
    private val taskLocalDataSource: TaskLocalDataSource,
    private val taskRemoteDataSource: TaskRemoteDataSource
) : TaskRepository {
    override fun getTasks(): Flow<List<Task>> {
        return taskLocalDataSource.fetchTasks()
    }

    override suspend fun getTask(id: String): Task {
        return taskLocalDataSource.getTask(id)
    }

    override suspend fun insertTask(task: Task) {
        taskRemoteDataSource.createTask(task)
        taskLocalDataSource.upsertTask(task)
    }

    override suspend fun updateTask(task: Task) {
        taskRemoteDataSource.updateTask(task)
        taskLocalDataSource.upsertTask(task)
    }

    override suspend fun deleteTask(id: String) {
        taskRemoteDataSource.deleteTask(id)
        taskLocalDataSource.deleteTasksById(listOf(id))
    }

    override suspend fun refreshTasks() {
        val localTasks = taskLocalDataSource.getTasks()
        val remoteTasks = taskRemoteDataSource.getTasks()
        val localIds = localTasks.map { it.id }.toSet()
        val remoteIds = remoteTasks.map { it.id }.toSet()
        val deleteIds = localIds.subtract(remoteIds).toList()

        taskLocalDataSource.upsertTasks(remoteTasks)
        taskLocalDataSource.deleteTasksById(deleteIds)
    }
}
