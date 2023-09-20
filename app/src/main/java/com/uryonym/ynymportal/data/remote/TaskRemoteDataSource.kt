package com.uryonym.ynymportal.data.remote

import com.uryonym.ynymportal.data.AuthRepository
import com.uryonym.ynymportal.data.model.RemoteTask
import com.uryonym.ynymportal.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

interface TaskRemoteDataSource {
    suspend fun getTasks(taskListId: String): List<RemoteTask>

    suspend fun getTask(id: String): RemoteTask

    suspend fun createTask(task: RemoteTask)

    suspend fun updateTask(id: String, task: RemoteTask)

    suspend fun deleteTask(id: String)
}

@Singleton
class TaskRemoteDataSourceImpl @Inject constructor(
    private val authRepository: AuthRepository,
    private val taskApiService: TaskApiService,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : TaskRemoteDataSource {
    override suspend fun getTasks(taskListId: String): List<RemoteTask> {
        val token = authRepository.getIdToken()

        return withContext(ioDispatcher) {
            taskApiService.getTasks(taskListId, token)
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

    override suspend fun updateTask(id: String, task: RemoteTask) {
        val token = authRepository.getIdToken()

        withContext(ioDispatcher) {
            taskApiService.updateTask(id, task, token)
        }
    }

    override suspend fun deleteTask(id: String) {
        val token = authRepository.getIdToken()

        withContext(ioDispatcher) {
            taskApiService.deleteTask(id, token)
        }
    }
}
