package com.uryonym.ynymportal.data.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.uryonym.ynymportal.data.Car
import com.uryonym.ynymportal.data.Confidential
import com.uryonym.ynymportal.data.Task
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

//private const val BASE_URL = "http:/10.0.2.2:3000/api/v1/"
private const val BASE_URL = "https://api-portal.uryonym.com/api/v1/"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
    .baseUrl(BASE_URL).build()

interface YnymPortalService {
    @GET("tasks")
    suspend fun getTasks(@Header("Authorization") token: String): List<NetworkTask>

    @GET("tasks/{id}")
    suspend fun getTask(@Path("id") id: String, @Header("Authorization") token: String): NetworkTask

    @POST("tasks")
    suspend fun addTask(@Body task: NetworkTask, @Header("Authorization") token: String): NetworkTask

    @PATCH("tasks/{id}")
    suspend fun editTask(
        @Path("id") id: String,
        @Body task: Task,
        @Header("Authorization") token: String
    ): NetworkTask

    @DELETE("tasks/{id}")
    suspend fun deleteTask(@Path("id") id: String, @Header("Authorization") token: String)

    @GET("confidentials")
    suspend fun getConfidentials(@Header("Authorization") token: String): List<Confidential>

    @GET("confidentials/{id}")
    suspend fun getConfidential(
        @Path("id") id: String,
        @Header("Authorization") token: String
    ): Confidential

    @POST("confidentials")
    suspend fun addConfidential(
        @Body confidential: Confidential,
        @Header("Authorization") token: String
    ): Confidential

    @PATCH("confidentials/{id}")
    suspend fun editConfidential(
        @Path("id") id: String,
        @Body confidential: Confidential, @Header("Authorization") token: String
    ): Confidential

    @DELETE("confidentials/{id}")
    suspend fun deleteConfidential(@Path("id") id: String, @Header("Authorization") token: String)

    @GET("cars")
    suspend fun getCars(@Header("Authorization") token: String): List<Car>

    @GET("cars/{id}")
    suspend fun getCar(@Path("id") id: String, @Header("Authorization") token: String): Car

    @POST("cars")
    suspend fun addCar(@Body car: Car, @Header("Authorization") token: String): Car

    @PATCH("cars/{id}")
    suspend fun editCar(
        @Path("id") id: String,
        @Body car: Car,
        @Header("Authorization") token: String
    ): Car

    @DELETE("cars/{id}")
    suspend fun deleteCar(@Path("id") id: String, @Header("Authorization") token: String)
}

object YnymPortalApi {
    val retrofitService: YnymPortalService by lazy {
        retrofit.create(YnymPortalService::class.java)
    }
}