package com.uryonym.ynymportal.data

import com.uryonym.ynymportal.data.network.YnymPortalApi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

interface TaskRepository {
    fun getTasks(): Flow<List<Task>>

    suspend fun getTask(id: String, token: String): Task

    suspend fun addTask(task: Task, token: String)

    suspend fun editTask(id: String, task: Task)

    suspend fun changeStatus(id: String, status: Boolean, token: String): Task

    suspend fun deleteTask(id: String, token: String)
}

class DefaultTaskRepository : TaskRepository {

    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
    private val authRepository: AuthRepository = DefaultAuthRepository()

    override fun getTasks(): Flow<List<Task>> = flow {
        val token = authRepository.getIdToken()
        emit(YnymPortalApi.retrofitService.getTasks(token = "Bearer $token"))
    }.flowOn(dispatcher)

    override suspend fun getTask(id: String, token: String): Task {
        return YnymPortalApi.retrofitService.getTask(id, token = "Bearer $token")
    }

    override suspend fun addTask(task: Task, token: String) {
        withContext(dispatcher) {
            YnymPortalApi.retrofitService.addTask(task, token = "Bearer $token")
        }
    }

    override suspend fun editTask(id: String, task: Task) {
        withContext(dispatcher) {
            val token = authRepository.getIdToken()
            YnymPortalApi.retrofitService.editTask(id, task, token = "Bearer $token")
        }
    }

    override suspend fun changeStatus(id: String, status: Boolean, token: String): Task {
        TODO("Not yet implemented")
    }

    override suspend fun deleteTask(id: String, token: String) {
        YnymPortalApi.retrofitService.deleteTask(id, token = "Bearer $token")
    }
}