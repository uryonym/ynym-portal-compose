package com.uryonym.ynymportal.data

import com.uryonym.ynymportal.data.network.YnymPortalApi

interface TaskRepository {
    suspend fun getTasks(token: String): List<Task>

    suspend fun getTask(id: String, token: String): Task

    suspend fun addTask(task: Task, token: String): Task

    suspend fun editTask(id: String, task: Task, token: String): Task

    suspend fun deleteTask(id: String, token: String)
}

class DefaultTaskRepository : TaskRepository {
    override suspend fun getTasks(token: String): List<Task> {
        return YnymPortalApi.retrofitService.getTasks(token = "Bearer $token")
    }

    override suspend fun getTask(id: String, token: String): Task {
        return YnymPortalApi.retrofitService.getTask(id, token = "Bearer $token")
    }

    override suspend fun addTask(task: Task, token: String): Task {
        return YnymPortalApi.retrofitService.addTask(task, token = "Bearer $token")
    }

    override suspend fun editTask(id: String, task: Task, token: String): Task {
        return YnymPortalApi.retrofitService.editTask(id, task, token = "Bearer $token")
    }

    override suspend fun deleteTask(id: String, token: String) {
        YnymPortalApi.retrofitService.deleteTask(id, token = "Bearer $token")
    }
}