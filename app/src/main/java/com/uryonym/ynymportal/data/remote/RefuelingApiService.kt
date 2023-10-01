package com.uryonym.ynymportal.data.remote

import com.uryonym.ynymportal.data.model.RemoteRefueling
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface RefuelingApiService {
    @GET("refuelings")
    suspend fun getRefuelings(
        @Header("Authorization") token: String
    ): List<RemoteRefueling>

    @GET("refuelings/{id}")
    suspend fun getRefueling(
        @Path("id") id: String,
        @Header("Authorization") token: String
    ): RemoteRefueling

    @POST("refuelings")
    suspend fun createRefueling(
        @Body refueling: RemoteRefueling,
        @Header("Authorization") token: String
    )

    @PATCH("refuelings/{id}")
    suspend fun updateRefueling(
        @Path("id") id: String,
        @Body refueling: RemoteRefueling,
        @Header("Authorization") token: String
    )

    @DELETE("refuelings/{id}")
    suspend fun deleteRefueling(@Path("id") id: String, @Header("Authorization") token: String)
}
