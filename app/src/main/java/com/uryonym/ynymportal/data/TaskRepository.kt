package com.uryonym.ynymportal.data

import androidx.room.Room
import com.uryonym.ynymportal.MyContext
import com.uryonym.ynymportal.data.local.TaskDao
import com.uryonym.ynymportal.data.local.TaskDatabase
import com.uryonym.ynymportal.data.network.YnymPortalApi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

interface TaskRepository {
    fun getTasks(): Flow<List<Task>>

    suspend fun getTask(id: String, token: String): Task

    suspend fun addTask(task: Task)

    suspend fun editTask(id: String, task: Task)

    suspend fun changeStatus(id: String, status: Boolean, token: String): Task

    suspend fun deleteTask(id: String, token: String)

    suspend fun refreshTask()
}

class DefaultTaskRepository : TaskRepository {

    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
    private val authRepository: AuthRepository = DefaultAuthRepository()
    private val localDataSource: TaskDao = Room.databaseBuilder(
        context = MyContext.getInstance().applicationContext,
        klass = TaskDatabase::class.java,
        name = "task-database"
    ).build().taskDao()

    override fun getTasks(): Flow<List<Task>> {
        return localDataSource.getTasks().map { tasks ->
            withContext(dispatcher) {
                tasks.toCommon()
            }
        }
    }

    override suspend fun getTask(id: String, token: String): Task {
        return YnymPortalApi.retrofitService.getTask(id, token = "Bearer $token").toCommon()
    }

    override suspend fun addTask(task: Task) {
        withContext(dispatcher) {
            localDataSource.upsertTask(task.toLocal())

            val token = authRepository.getIdToken()
            YnymPortalApi.retrofitService.addTask(task.toNetwork(), token = "Bearer $token")
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

    override suspend fun refreshTask() {
        withContext(dispatcher) {
            val token = authRepository.getIdToken()
            val tasks = YnymPortalApi.retrofitService.getTasks(token).toCommon()
            localDataSource.deleteAllTask()
            tasks.map {
                localDataSource.upsertTask(it.toLocal())
            }
        }
    }
}