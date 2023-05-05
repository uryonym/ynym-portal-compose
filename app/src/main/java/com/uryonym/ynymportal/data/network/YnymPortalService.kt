package com.uryonym.ynymportal.data.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.uryonym.ynymportal.data.Task
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

private const val BASE_URL = "http:/10.0.2.2:3000/api/v1/"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(Json.asConverterFactory(MediaType.get("application/json")))
    .baseUrl(BASE_URL).build()

interface YnymPortalService {
    @GET("tasks")
    suspend fun getTasks(): List<Task>

    @POST("tasks")
    suspend fun addTask(@Body task: Task)
}

object YnymPortalApi {
    val retrofitService: YnymPortalService by lazy {
        retrofit.create(YnymPortalService::class.java)
    }
}