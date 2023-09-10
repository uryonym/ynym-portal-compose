package com.uryonym.ynymportal.data

import android.util.Log
import com.uryonym.ynymportal.data.local.TaskDao
import com.uryonym.ynymportal.data.local.TaskListDao
import com.uryonym.ynymportal.data.model.Task
import com.uryonym.ynymportal.data.model.TaskList
import com.uryonym.ynymportal.data.network.NetworkTask
import com.uryonym.ynymportal.data.network.YnymPortalApi
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate
import javax.inject.Inject
import javax.inject.Singleton

interface TaskRepository {
    fun getTaskLists(): Flow<List<TaskList>>

    suspend fun refreshTaskLists()

    fun getTasks(): Flow<List<Task>>

    suspend fun getTask(id: String): Task

    suspend fun insertTask(
        title: String,
        description: String,
        deadLine: LocalDate?,
        taskListId: String
    )

    suspend fun updateTask(
        id: String,
        title: String,
        description: String,
        deadLine: LocalDate?,
        isComplete: Boolean
    )

    suspend fun changeStatus(taskId: String, status: Boolean)

    suspend fun deleteTask(id: String)

    suspend fun refreshTasks()

    suspend fun refreshTask(id: String): Task
}

@Singleton
class TaskRepositoryImpl @Inject constructor(
    private val localDataSource: TaskDao,
    private val taskListDataSource: TaskListDao,
    private val authRepository: AuthRepository
) : TaskRepository {

    override fun getTaskLists(): Flow<List<TaskList>> {
        return taskListDataSource.getTaskLists(authRepository.uid)
    }

    override suspend fun refreshTaskLists() {
        val token = authRepository.getIdToken()
        val taskLists = YnymPortalApi.retrofitService.getTaskLists(token).toLocal()
        taskListDataSource.deleteAllTaskList()
        taskLists.map {
            taskListDataSource.insertTaskList(it)
        }
    }

    override fun getTasks(): Flow<List<Task>> {
        return localDataSource.getTasks()
    }

    override suspend fun getTask(id: String): Task {
        return localDataSource.getTask(id)
    }

    override suspend fun insertTask(
        title: String,
        description: String,
        deadLine: LocalDate?,
        taskListId: String
    ) {
        val task = Task(
            title = title,
            description = description,
            deadLine = deadLine,
            isComplete = false,
            uid = authRepository.uid,
            taskListId = taskListId
        )
        val token = authRepository.getIdToken()
        val networkTask = YnymPortalApi.retrofitService.addTask(task.toNetwork(), "Bearer $token")
        Log.d("debug", networkTask.toString())

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
            isComplete = isComplete,
            uid = authRepository.uid,
            taskListId = ""
        )
        val token = authRepository.getIdToken()
        val networkTask =
            YnymPortalApi.retrofitService.editTask(id, task.toNetwork(), "Bearer $token")

        localDataSource.updateTask(networkTask.toLocal())
    }

    override suspend fun changeStatus(taskId: String, status: Boolean) {
        val task = NetworkTask(
            id = taskId,
            isComplete = status
        )

        val token = authRepository.getIdToken()
        val networkTask =
            YnymPortalApi.retrofitService.editTask(taskId, task, "Bearer $token")

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
