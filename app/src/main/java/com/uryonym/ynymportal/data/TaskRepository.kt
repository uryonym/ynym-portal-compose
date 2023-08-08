package com.uryonym.ynymportal.data

import com.uryonym.ynymportal.data.local.TaskDao
import com.uryonym.ynymportal.data.model.Task
import com.uryonym.ynymportal.data.network.YnymPortalApi
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate
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

    suspend fun changeStatus(task: Task, status: Boolean)

    suspend fun deleteTask(id: String)

    suspend fun refreshTasks()

    suspend fun refreshTask(id: String): Task
}

@Singleton
class TaskRepositoryImpl @Inject constructor(
    private val localDataSource: TaskDao
) : TaskRepository {

    private val authRepository = DefaultAuthRepository()

    override fun getTasks(): Flow<List<Task>> {
        return localDataSource.getTasks()
    }

    override suspend fun getTask(id: String): Task {
        return localDataSource.getTask(id)
    }

    override suspend fun insertTask(title: String, description: String, deadLine: LocalDate?) {
        val task = Task(
            id = "",
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

    override suspend fun changeStatus(task: Task, status: Boolean) {
        task.isComplete = status

        val token = authRepository.getIdToken()
        val networkTask =
            YnymPortalApi.retrofitService.editTask(task.id, task.toNetwork(), "Bearer $token")

        localDataSource.updateTask(networkTask.toLocal())
    }

    override suspend fun deleteTask(id: String) {
        val token = authRepository.getIdToken()
        YnymPortalApi.retrofitService.deleteTask(id, token = "Bearer $token")

        localDataSource.deleteTask(id)
    }

    override suspend fun refreshTasks() {
        val token = authRepository.getIdToken()
        val tasks = YnymPortalApi.retrofitService.getTasks(token).toLocal()
        localDataSource.deleteAllTask()
        tasks.map {
            localDataSource.insertTask(it)
        }
    }

    override suspend fun refreshTask(id: String): Task {
        val token = authRepository.getIdToken()
        val task = YnymPortalApi.retrofitService.getTask(id, token = "Bearer $token").toLocal()
        localDataSource.insertTask(task)

        return task
    }
}