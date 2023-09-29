package com.uryonym.ynymportal.data.remote

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

//private const val BASE_URL = "http:/10.0.2.2:3000/api/v1/"
private const val BASE_URL = "https://api-portal.uryonym.com/api/v1/"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(Json.asConverterFactory(MediaType.get("application/json")))
    .baseUrl(BASE_URL).build()

object YnymPortalApi {
    val retrofitService: YnymPortalService by lazy {
        retrofit.create(YnymPortalService::class.java)
    }
}

interface YnymPortalService {
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

