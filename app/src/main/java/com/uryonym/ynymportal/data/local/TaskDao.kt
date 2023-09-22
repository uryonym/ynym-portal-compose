package com.uryonym.ynymportal.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.uryonym.ynymportal.data.model.LocalTask
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Query("SELECT * FROM task ORDER BY dead_line is null, dead_line, created_at")
    fun fetchTasks(): Flow<List<LocalTask>>

    @Query("SELECT * FROM task ORDER BY dead_line is null, dead_line, created_at")
    suspend fun getTasks(): List<LocalTask>

    @Upsert
    suspend fun upsertTask(task: LocalTask)

    @Upsert
    suspend fun upsertTasks(tasks: List<LocalTask>)

    @Query("DELETE FROM task WHERE id = (:id)")
    suspend fun deleteTaskById(id: String)

//    @Query("SELECT * FROM task WHERE id = (:taskId)")
//    suspend fun getTask(taskId: String): Task
//
//    @Query("DELETE FROM task WHERE id = (:taskId)")
//    suspend fun deleteTask(taskId: String)
//
//    @Query("DELETE FROM task")
//    suspend fun deleteAllTask()
}
