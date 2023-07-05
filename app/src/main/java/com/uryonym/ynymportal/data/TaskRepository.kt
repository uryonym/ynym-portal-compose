package com.uryonym.ynymportal.data

import com.uryonym.ynymportal.data.network.YnymPortalApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface TaskRepository {
    suspend fun getTasks(): Flow<List<Task>>

    suspend fun getTask(id: String): Task

    suspend fun addTask(task: Task): Task

    suspend fun editTask(id: String, task: Task): Task

    suspend fun deleteTask(id: String)
}

class DefaultTaskRepository: TaskRepository {
    override suspend fun getTasks(): Flow<List<Task>> = flow {
        emit(YnymPortalApi.retrofitService.getTasks())
    }

    override suspend fun getTask(id: String): Task {
        return YnymPortalApi.retrofitService.getTask(id)
    }

    override suspend fun addTask(task: Task): Task {
        return YnymPortalApi.retrofitService.addTask(task)
    }

    override suspend fun editTask(id: String, task: Task): Task {
        return YnymPortalApi.retrofitService.editTask(id, task)
    }

    override suspend fun deleteTask(id: String) {
        YnymPortalApi.retrofitService.deleteTask(id)
    }
}