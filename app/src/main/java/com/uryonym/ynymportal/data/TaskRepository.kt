package com.uryonym.ynymportal.data

import com.uryonym.ynymportal.data.local.TaskLocalDataSource
import com.uryonym.ynymportal.data.model.Task
import com.uryonym.ynymportal.data.remote.TaskRemoteDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

interface TaskRepository {
    fun getTasks(): Flow<List<Task>>

    suspend fun insertTask(task: Task)

    suspend fun updateTask(task: Task)

    suspend fun refreshTasks()

//    suspend fun deleteTask(id: String)
//
//    suspend fun refreshTask(id: String): Task
}

@Singleton
class TaskRepositoryImpl @Inject constructor(
    private val taskLocalDataSource: TaskLocalDataSource,
    private val taskRemoteDataSource: TaskRemoteDataSource
) : TaskRepository {
    override fun getTasks(): Flow<List<Task>> {
        return taskLocalDataSource.fetchTasks()
    }

    override suspend fun insertTask(task: Task) {
        taskRemoteDataSource.createTask(task)
        taskLocalDataSource.upsertTask(task)
    }

    override suspend fun updateTask(task: Task) {
        taskRemoteDataSource.updateTask(task)
        taskLocalDataSource.upsertTask(task)
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

//    override suspend fun deleteTask(id: String) {
//        val token = authRepository.getIdToken()
//        YnymPortalApi.retrofitService.deleteTask(id, token = "Bearer $token")
//
//        localDataSource.deleteTask(id)
//    }
//
//    override suspend fun refreshTask(id: String): Task {
//        val token = authRepository.getIdToken()
//        val task = YnymPortalApi.retrofitService.getTask(id, token = "Bearer $token").toLocal()
//        localDataSource.insertTask(task)
//
//        return task
//    }

}
