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

    @Insert
    suspend fun insertTask(task: LocalTask)

    @Update
    suspend fun updateTask(task: LocalTask)

    @Query("DELETE FROM task WHERE id = (:taskId)")
    suspend fun deleteTask(taskId: String)

    @Query("DELETE FROM task")
    suspend fun deleteAllTask()
}
