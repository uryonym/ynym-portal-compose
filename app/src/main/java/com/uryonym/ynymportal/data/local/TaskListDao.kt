package com.uryonym.ynymportal.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.uryonym.ynymportal.data.model.TaskList
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskListDao {
    @Query("SELECT * FROM task_list WHERE uid = :uid")
    fun getTaskLists(uid: String): Flow<List<TaskList>>

    //    @Query("SELECT * FROM task WHERE id = (:taskId)")
//    suspend fun getTask(taskId: String): Task
//
    @Insert
    suspend fun insertTaskList(taskList: TaskList)

    //    @Update
//    suspend fun updateTask(task: Task)
//
//    @Query("DELETE FROM task WHERE id = (:taskId)")
//    suspend fun deleteTask(taskId: String)
//
    @Query("DELETE FROM task_list")
    suspend fun deleteAllTaskList()
}
