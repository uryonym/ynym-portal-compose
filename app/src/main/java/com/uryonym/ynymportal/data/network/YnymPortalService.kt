package com.uryonym.ynymportal.data.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.uryonym.ynymportal.data.Confidential
import com.uryonym.ynymportal.data.Task
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

//private const val BASE_URL = "http:/10.0.2.2:3000/api/v1/"
private const val BASE_URL = "https://api-portal.uryonym.com/api/v1/"

private val logger = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
private val httpClient = OkHttpClient.Builder().addInterceptor(logger)

private val retrofit = Retrofit.Builder()
    .addConverterFactory(Json.asConverterFactory(MediaType.get("application/json")))
    .baseUrl(BASE_URL).build()

interface YnymPortalService {
    @GET("tasks")
    suspend fun getTasks(): List<Task>

    @GET("tasks/{id}")
    suspend fun getTask(@Path("id") id: String): Task

    @POST("tasks")
    suspend fun addTask(@Body task: Task): Task

    @PATCH("tasks/{id}")
    suspend fun editTask(@Path("id") id: String, @Body task: Task): Task

    @DELETE("tasks/{id}")
    suspend fun deleteTask(@Path("id") id: String)

    @GET("confidentials")
    suspend fun getConfidentials(): List<Confidential>

    @GET("confidentials/{id}")
    suspend fun getConfidential(@Path("id") id: String): Confidential

    @POST("confidentials")
    suspend fun addConfidential(@Body confidential: Confidential): Confidential

    @PATCH("confidentials/{id}")
    suspend fun editConfidential(
        @Path("id") id: String,
        @Body confidential: Confidential
    ): Confidential

    @DELETE("confidentials/{id}")
    suspend fun deleteConfidential(@Path("id") id: String)
}

object YnymPortalApi {
    val retrofitService: YnymPortalService by lazy {
        retrofit.create(YnymPortalService::class.java)
    }
}