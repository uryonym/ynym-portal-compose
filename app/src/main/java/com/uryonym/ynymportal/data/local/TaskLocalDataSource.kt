package com.uryonym.ynymportal.data.local

import com.uryonym.ynymportal.data.model.Task
import com.uryonym.ynymportal.data.model.toLocal
import com.uryonym.ynymportal.data.model.toModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

interface TaskLocalDataSource {
    fun fetchTasks(): Flow<List<Task>>

    suspend fun getTasks(): List<Task>

    suspend fun upsertTask(task: Task)

    suspend fun upsertTasks(tasks: List<Task>)

    suspend fun deleteTasksById(ids: List<String>)
}

@Singleton
class TaskLocalDataSourceImpl @Inject constructor(
    private val taskDao: TaskDao
) : TaskLocalDataSource {
    override fun fetchTasks(): Flow<List<Task>> {
        return taskDao.fetchTasks().map { it.toModel() }
    }

    override suspend fun getTasks(): List<Task> {
        return taskDao.getTasks().toModel()
    }

    override suspend fun upsertTask(task: Task) {
        taskDao.upsertTask(task.toLocal())
    }

    override suspend fun upsertTasks(tasks: List<Task>) {
        taskDao.upsertTasks(tasks.toLocal())
    }

    override suspend fun deleteTasksById(ids: List<String>) {
        ids.forEach { taskDao.deleteTaskById(it) }
    }
}
