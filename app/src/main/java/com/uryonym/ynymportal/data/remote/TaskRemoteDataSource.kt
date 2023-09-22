package com.uryonym.ynymportal.data.remote

import com.uryonym.ynymportal.data.AuthRepository
import com.uryonym.ynymportal.data.model.RemoteTask
import com.uryonym.ynymportal.data.model.Task
import com.uryonym.ynymportal.data.model.toModel
import com.uryonym.ynymportal.data.model.toRemote
import com.uryonym.ynymportal.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

interface TaskRemoteDataSource {
    fun fetchTasks(): Flow<List<Task>>

    suspend fun getTasks(): List<Task>

    suspend fun getTask(id: String): RemoteTask

    suspend fun createTask(task: RemoteTask)

    suspend fun updateTask(task: Task)

    suspend fun deleteTask(id: String)
}

@Singleton
class TaskRemoteDataSourceImpl @Inject constructor(
    private val authRepository: AuthRepository,
    private val taskApiService: TaskApiService,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : TaskRemoteDataSource {
    override fun fetchTasks(): Flow<List<Task>> = flow {
        val token = authRepository.getIdToken()
        withContext(ioDispatcher) {
            emit(taskApiService.getTasks(token).toModel())
        }
    }

    override suspend fun getTasks(): List<Task> {
        val token = authRepository.getIdToken()
        return withContext(ioDispatcher) {
            taskApiService.getTasks(token).toModel()
        }
    }

    override suspend fun getTask(id: String): RemoteTask {
        val token = authRepository.getIdToken()

        return withContext(ioDispatcher) {
            taskApiService.getTask(id, token)
        }
    }

    override suspend fun createTask(task: RemoteTask) {
        val token = authRepository.getIdToken()

        withContext(ioDispatcher) {
            taskApiService.createTask(task, token)
        }
    }

    override suspend fun updateTask(task: Task) {
        val token = authRepository.getIdToken()

        withContext(ioDispatcher) {
            taskApiService.updateTask(task.id, task.toRemote(), token)
        }
    }

    override suspend fun deleteTask(id: String) {
        val token = authRepository.getIdToken()

        withContext(ioDispatcher) {
            taskApiService.deleteTask(id, token)
        }
    }
}
