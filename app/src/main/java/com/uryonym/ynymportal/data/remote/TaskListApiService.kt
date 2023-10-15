package com.uryonym.ynymportal.data.remote

import com.uryonym.ynymportal.data.model.RemoteTaskList
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface TaskListApiService {
    @GET("task_lists")
    suspend fun getTaskLists(@Header("Authorization") token: String): List<RemoteTaskList>

    @GET("task_lists/{id}")
    suspend fun getTaskList(
        @Path("id") id: String,
        @Header("Authorization") token: String
    ): RemoteTaskList

    @POST("task_lists")
    suspend fun createTaskList(
        @Body taskList: RemoteTaskList,
        @Header("Authorization") token: String
    ): RemoteTaskList

    @PATCH("task_lists/{id}")
    suspend fun updateTaskList(
        @Path("id") id: String,
        @Body taskList: RemoteTaskList,
        @Header("Authorization") token: String
    )

    @DELETE("task_lists/{id}")
    suspend fun deleteTaskList(@Path("id") id: String, @Header("Authorization") token: String)
}
