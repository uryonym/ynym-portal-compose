package com.uryonym.ynymportal.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Query("SELECT * FROM task")
    fun getTasks(): Flow<List<LocalTask>>

    @Query("SELECT * FROM task WHERE id = (:taskId)")
    suspend fun getTask(taskId: String): LocalTask

    @Upsert
    suspend fun upsertTask(task: LocalTask)

    @Update
    suspend fun updateTask(vararg  task: LocalTask)

    @Query("DELETE FROM task WHERE id = (:taskId)")
    suspend fun deleteTask(taskId: String)

    @Query("DELETE FROM task")
    suspend fun deleteAllTask()
}
