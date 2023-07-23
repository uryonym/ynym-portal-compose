package com.uryonym.ynymportal.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Query("SELECT * FROM task")
    fun getTasks(): Flow<List<LocalTask>>

    @Query("SELECT * FROM task WHERE id = (:taskId)")
    fun getTask(taskId: String): Flow<LocalTask>

    @Insert
    suspend fun insertTask(vararg task: LocalTask)

    @Delete
    suspend fun deleteTask(task: LocalTask)

    @Query("DELETE FROM task")
    suspend fun deleteAllTask()
}
