package com.uryonym.ynymportal.data.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

private const val BASE_URL = "http:/10.0.2.2:3000/api/v1/"
//private const val BASE_URL = "https://api-portal.uryonym.com/api/v1/"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(Json.asConverterFactory(MediaType.get("application/json")))
    .baseUrl(BASE_URL).build()

interface YnymPortalService {
    @GET("task_lists")
    suspend fun getTaskLists(@Header("Authorization") token: String): List<NetworkTaskList>

    @GET("tasks")
    suspend fun getTasks(@Header("Authorization") token: String): List<NetworkTask>

    @GET("tasks/{id}")
    suspend fun getTask(@Path("id") id: String, @Header("Authorization") token: String): NetworkTask

    @POST("tasks")
    suspend fun addTask(
        @Body task: NetworkTask,
        @Header("Authorization") token: String
    ): NetworkTask

    @PATCH("tasks/{id}")
    suspend fun editTask(
        @Path("id") id: String,
        @Body task: NetworkTask,
        @Header("Authorization") token: String
    ): NetworkTask

    @DELETE("tasks/{id}")
    suspend fun deleteTask(@Path("id") id: String, @Header("Authorization") token: String)

    @GET("confidentials")
    suspend fun getConfidentials(@Header("Authorization") token: String): List<NetworkConfidential>

    @GET("confidentials/{id}")
    suspend fun getConfidential(
        @Path("id") id: String,
        @Header("Authorization") token: String
    ): NetworkConfidential

    @POST("confidentials")
    suspend fun addConfidential(
        @Body confidential: NetworkConfidential,
        @Header("Authorization") token: String
    ): NetworkConfidential

    @PATCH("confidentials/{id}")
    suspend fun editConfidential(
        @Path("id") id: String,
        @Body confidential: NetworkConfidential,
        @Header("Authorization") token: String
    ): NetworkConfidential

    @DELETE("confidentials/{id}")
    suspend fun deleteConfidential(@Path("id") id: String, @Header("Authorization") token: String)

    @GET("cars")
    suspend fun getCars(@Header("Authorization") token: String): List<NetworkCar>

    @GET("cars/{id}")
    suspend fun getCar(@Path("id") id: String, @Header("Authorization") token: String): NetworkCar

    @POST("cars")
    suspend fun addCar(@Body car: NetworkCar, @Header("Authorization") token: String): NetworkCar

    @PATCH("cars/{id}")
    suspend fun editCar(
        @Path("id") id: String,
        @Body car: NetworkCar,
        @Header("Authorization") token: String
    ): NetworkCar

    @DELETE("cars/{id}")
    suspend fun deleteCar(@Path("id") id: String, @Header("Authorization") token: String)

    @GET("refuelings")
    suspend fun getRefuelings(@Header("Authorization") token: String): List<NetworkRefueling>

    @GET("refuelings/{id}")
    suspend fun getRefueling(
        @Path("id") id: String,
        @Header("Authorization") token: String
    ): NetworkRefueling

    @POST("refuelings")
    suspend fun addRefueling(
        @Body refueling: NetworkRefueling,
        @Header("Authorization") token: String
    ): NetworkRefueling

    @PATCH("refuelings/{id}")
    suspend fun editRefueling(
        @Path("id") id: String,
        @Body refueling: NetworkRefueling,
        @Header("Authorization") token: String
    ): NetworkRefueling

    @DELETE("refuelings/{id}")
    suspend fun deleteRefueling(@Path("id") id: String, @Header("Authorization") token: String)
}

object YnymPortalApi {
    val retrofitService: YnymPortalService by lazy {
        retrofit.create(YnymPortalService::class.java)
    }
}
