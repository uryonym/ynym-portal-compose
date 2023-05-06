package com.uryonym.ynymportal.data.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.uryonym.ynymportal.data.AuthInfo
import com.uryonym.ynymportal.data.Task
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

private const val BASE_URL = "http:/10.0.2.2:3000/api/v1/"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(Json.asConverterFactory(MediaType.get("application/json")))
    .baseUrl(BASE_URL).build()

interface YnymPortalService {
    @GET("tasks")
    suspend fun getTasks(): List<Task>

    @POST("tasks")
    suspend fun addTask(@Body task: Task)

    @PATCH("tasks/{id}")
    suspend fun editTask(@Path("id") id: String, @Body task: Task)

    @DELETE("tasks/{id}")
    suspend fun deleteTask(@Path("id") id: String)

    @GET("auth_infos")
    suspend fun getAuthInfos(): List<AuthInfo>

    @POST("auth_infos")
    suspend fun addAuthInfo(@Body authInfo: AuthInfo)

    @PATCH("auth_infos/{id}")
    suspend fun editAuthInfo(@Path("id") id: String, @Body authInfo: AuthInfo)

    @DELETE("auth_infos/{id}")
    suspend fun deleteAuthInfo(@Path("id") id: String)
}

object YnymPortalApi {
    val retrofitService: YnymPortalService by lazy {
        retrofit.create(YnymPortalService::class.java)
    }
}