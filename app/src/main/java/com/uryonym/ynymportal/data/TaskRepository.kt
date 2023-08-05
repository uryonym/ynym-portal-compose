package com.uryonym.ynymportal.data

import com.uryonym.ynymportal.data.local.TaskDao
import com.uryonym.ynymportal.data.network.YnymPortalApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.LocalDate
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

interface TaskRepository {
    fun getTasks(): Flow<List<Task>>

    suspend fun getTask(id: String): Task

    suspend fun insertTask(title: String, description: String, deadLine: LocalDate?)

    suspend fun updateTask(
        id: String,
        title: String,
        description: String,
        deadLine: LocalDate?,
        isComplete: Boolean
    )

    suspend fun changeStatus(id: String, status: Boolean, token: String): Task

    suspend fun deleteTask(id: String)

    suspend fun refreshTasks()

    suspend fun refreshTask(id: String): Task
}

@Singleton
class TaskRepositoryImpl @Inject constructor(
    private val localDataSource: TaskDao
) : TaskRepository {

    private val authRepository: AuthRepository = DefaultAuthRepository()

    override fun getTasks(): Flow<List<Task>> {
        return localDataSource.getTasks().map { tasks ->
            tasks.toCommon()
        }
    }

    override suspend fun getTask(id: String): Task {
        return localDataSource.getTask(id).toCommon()
    }

    override suspend fun insertTask(title: String, description: String, deadLine: LocalDate?) {
        val task = Task(
            title = title,
            description = description,
            deadLine = deadLine,
            isComplete = false
        )
        val token = authRepository.getIdToken()
        val networkTask = YnymPortalApi.retrofitService.addTask(task.toNetwork(), "Bearer $token")

        localDataSource.insertTask(networkTask.toLocal())
    }

    override suspend fun updateTask(
        id: String,
        title: String,
        description: String,
        deadLine: LocalDate?,
        isComplete: Boolean
    ) {
        val task = Task(
            id = id,
            title = title,
            description = description,
            deadLine = deadLine,
            isComplete = isComplete
        )
        val token = authRepository.getIdToken()
        val networkTask =
            YnymPortalApi.retrofitService.editTask(id, task.toNetwork(), "Bearer $token")

        localDataSource.updateTask(networkTask.toLocal())
    }

    override suspend fun changeStatus(id: String, status: Boolean, token: String): Task {
        TODO("Not yet implemented")
    }

    override suspend fun deleteTask(id: String) {
        localDataSource.deleteTask(id)

        val token = authRepository.getIdToken()
        YnymPortalApi.retrofitService.deleteTask(id, token = "Bearer $token")
    }

    override suspend fun refreshTasks() {
        val token = authRepository.getIdToken()
        val tasks = YnymPortalApi.retrofitService.getTasks(token).toCommon()
        localDataSource.deleteAllTask()
        tasks.map {
            localDataSource.insertTask(it.toLocal())
        }
    }

    override suspend fun refreshTask(id: String): Task {
        val token = authRepository.getIdToken()
        val networkTask = YnymPortalApi.retrofitService.getTask(id, token = "Bearer $token")
        localDataSource.insertTask(networkTask.toLocal())
        return networkTask.toCommon()
    }
}