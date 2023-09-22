package com.uryonym.ynymportal.data.remote

import com.uryonym.ynymportal.data.model.RemoteTask
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface TaskApiService {
    @GET("tasks")
    suspend fun getTasks(
        @Header("Authorization") token: String
    ): List<RemoteTask>

    @GET("tasks/{id}")
    suspend fun getTask(
        @Path("id") id: String,
        @Header("Authorization") token: String
    ): RemoteTask

    @POST("tasks")
    suspend fun createTask(
        @Body task: RemoteTask,
        @Header("Authorization") token: String
    ): RemoteTask

    @PATCH("tasks/{id}")
    suspend fun updateTask(
        @Path("id") id: String,
        @Body task: RemoteTask,
        @Header("Authorization") token: String
    )

    @DELETE("tasks/{id}")
    suspend fun deleteTask(@Path("id") id: String, @Header("Authorization") token: String)
}
