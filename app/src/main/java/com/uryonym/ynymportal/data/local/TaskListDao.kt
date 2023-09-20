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

    @Upsert
    suspend fun upsertTaskLists(taskLists: List<LocalTaskList>)

    @Query("DELETE FROM task_list WHERE id = (:id)")
    suspend fun deleteTaskListById(id: String)

    //    @Query("SELECT * FROM task WHERE id = (:taskId)")
//    suspend fun getTask(taskId: String): Task
//
//    @Insert
//    suspend fun insertTaskList(taskList: TaskList)

    //    @Update
//    suspend fun updateTask(task: Task)

//    @Query("DELETE FROM task WHERE id = (:taskId)")
//    suspend fun deleteTask(taskId: String)
//
//    @Query("DELETE FROM task_list")
//    suspend fun deleteAllTaskList()
}
