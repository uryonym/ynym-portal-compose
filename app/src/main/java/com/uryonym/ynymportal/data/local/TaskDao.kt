package com.uryonym.ynymportal.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.uryonym.ynymportal.data.model.Task
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Query("SELECT * FROM task ORDER BY dead_line is null, dead_line, created_at")
    fun getTasks(): Flow<List<Task>>

    @Query("SELECT * FROM task WHERE id = (:taskId)")
    suspend fun getTask(taskId: String): Task

    @Insert
    suspend fun insertTask(task: Task)

    @Update
    suspend fun updateTask(task: Task)

    @Query("DELETE FROM task WHERE id = (:taskId)")
    suspend fun deleteTask(taskId: String)

    @Query("DELETE FROM task")
    suspend fun deleteAllTask()
}
