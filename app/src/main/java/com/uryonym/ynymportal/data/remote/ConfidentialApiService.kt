package com.uryonym.ynymportal.data.remote

import com.uryonym.ynymportal.data.model.RemoteConfidential
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface ConfidentialApiService {
    @GET("confidentials")
    suspend fun getConfidentials(
        @Header("Authorization") token: String
    ): List<RemoteConfidential>

    @GET("confidentials/{id}")
    suspend fun getConfidential(
        @Path("id") id: String,
        @Header("Authorization") token: String
    ): RemoteConfidential

    @POST("confidentials")
    suspend fun createConfidential(
        @Body confidential: RemoteConfidential,
        @Header("Authorization") token: String
    )

    @PATCH("confidentials/{id}")
    suspend fun updateConfidential(
        @Path("id") id: String,
        @Body confidential: RemoteConfidential,
        @Header("Authorization") token: String
    )

    @DELETE("confidentials/{id}")
    suspend fun deleteConfidential(@Path("id") id: String, @Header("Authorization") token: String)
}
