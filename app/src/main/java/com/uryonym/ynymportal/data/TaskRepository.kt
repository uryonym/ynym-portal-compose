package com.uryonym.ynymportal.data

import com.uryonym.ynymportal.data.local.TaskListLocalDataSource
import com.uryonym.ynymportal.data.model.RemoteTaskList
import com.uryonym.ynymportal.data.model.Task
import com.uryonym.ynymportal.data.model.TaskList
import com.uryonym.ynymportal.data.remote.TaskListRemoteDataSource
import com.uryonym.ynymportal.data.remote.YnymPortalApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

interface TaskRepository {
//    fun getTasks(taskListId: String): Flow<List<Task>>
//
//    suspend fun insertTask(task: Task)
//
//    suspend fun updateTask(taskListId: String, task: Task)

//    suspend fun deleteTask(id: String)
//
//    suspend fun refreshTask(id: String): Task
}

@Singleton
class TaskRepositoryImpl @Inject constructor(
    private val taskListLocalDataSource: TaskListLocalDataSource,
    private val taskListRemoteDataSource: TaskListRemoteDataSource
) : TaskRepository {

//    override fun getTaskLists(): Flow<List<TaskList>> {
//        return taskListLocalDataSource.getTaskLists(
//    }
//
//    override fun getTasks(taskListId: String): Flow<List<Task>> = flow {
//        val token = authRepository.getIdToken()
//        emit(YnymPortalApi.retrofitService.getTasks(taskListId, token))
//    }

    //    override suspend fun getTask(id: String): Task {
//        return localDataSource.getTask(id)
//    }
//
//    override suspend fun insertTask(task: Task) {
//        val token = authRepository.getIdToken()
//        YnymPortalApi.retrofitService.addTask(task, "Bearer $token")
//    }
//
//    override suspend fun updateTask(taskListId: String, task: Task) {
//        val token = authRepository.getIdToken()
//        YnymPortalApi.retrofitService.editTask(taskListId, task.id, task, "Bearer $token")
//    }

//    override suspend fun deleteTask(id: String) {
//        val token = authRepository.getIdToken()
//        YnymPortalApi.retrofitService.deleteTask(id, token = "Bearer $token")
//
//        localDataSource.deleteTask(id)
//    }
//
//    override suspend fun refreshTask(id: String): Task {
//        val token = authRepository.getIdToken()
//        val task = YnymPortalApi.retrofitService.getTask(id, token = "Bearer $token").toLocal()
//        localDataSource.insertTask(task)
//
//        return task
//    }

}
