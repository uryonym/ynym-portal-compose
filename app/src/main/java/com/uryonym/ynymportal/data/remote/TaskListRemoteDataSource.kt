package com.uryonym.ynymportal.data.remote

import com.uryonym.ynymportal.data.AuthRepository
import com.uryonym.ynymportal.data.model.RemoteTaskList
import com.uryonym.ynymportal.data.model.TaskList
import com.uryonym.ynymportal.data.model.toModel
import com.uryonym.ynymportal.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

interface TaskListRemoteDataSource {
    fun fetchTaskLists(): Flow<List<TaskList>>

    suspend fun getTaskLists(): List<TaskList>

    suspend fun getTaskList(id: String): RemoteTaskList

    suspend fun createTaskList(taskList: RemoteTaskList)

    suspend fun updateTaskList(id: String, taskList: RemoteTaskList)

    suspend fun deleteTaskList(id: String)
}

@Singleton
class TaskListRemoteDataSourceImpl @Inject constructor(
    private val authRepository: AuthRepository,
    private val taskListApiService: TaskListApiService,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : TaskListRemoteDataSource {
    override fun fetchTaskLists(): Flow<List<TaskList>> = flow {
        val token = authRepository.getIdToken()
        withContext(ioDispatcher) {
            emit(taskListApiService.getTaskLists(token).toModel())
        }
    }

    override suspend fun getTaskLists(): List<TaskList> {
        val token = authRepository.getIdToken()
        return withContext(ioDispatcher) {
            taskListApiService.getTaskLists(token).toModel()
        }
    }

    override suspend fun getTaskList(id: String): RemoteTaskList {
        val token = authRepository.getIdToken()

        return withContext(ioDispatcher) {
            taskListApiService.getTaskList(id, token)
        }
    }

    override suspend fun createTaskList(taskList: RemoteTaskList) {
        val token = authRepository.getIdToken()

        withContext(ioDispatcher) {
            taskListApiService.createTaskList(taskList, token)
        }
    }

    override suspend fun updateTaskList(id: String, taskList: RemoteTaskList) {
        val token = authRepository.getIdToken()

        withContext(ioDispatcher) {
            taskListApiService.updateTaskList(id, taskList, token)
        }
    }

    override suspend fun deleteTaskList(id: String) {
        val token = authRepository.getIdToken()

        withContext(ioDispatcher) {
            taskListApiService.deleteTaskList(id, token)
        }
    }
}
