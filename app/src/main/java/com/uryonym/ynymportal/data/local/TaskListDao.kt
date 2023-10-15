package com.uryonym.ynymportal.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.uryonym.ynymportal.data.model.LocalTaskList
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskListDao {
    @Query("SELECT * FROM task_list")
    fun fetchTaskLists(): Flow<List<LocalTaskList>>

    @Query("SELECT * FROM task_list")
    suspend fun getTaskLists(): List<LocalTaskList>

    @Query("SELECT * FROM task_list WHERE id = (:id)")
    suspend fun getTaskList(id: String): LocalTaskList

    @Upsert
    suspend fun upsertTaskList(taskList: LocalTaskList)

    @Upsert
    suspend fun upsertTaskLists(taskLists: List<LocalTaskList>)

    @Query("DELETE FROM task_list WHERE id = (:id)")
    suspend fun deleteTaskListById(id: String)
}
