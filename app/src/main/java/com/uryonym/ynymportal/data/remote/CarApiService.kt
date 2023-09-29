package com.uryonym.ynymportal.data.remote

import com.uryonym.ynymportal.data.model.RemoteCar
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface CarApiService {
    @GET("cars")
    suspend fun getCars(
        @Header("Authorization") token: String
    ): List<RemoteCar>

    @GET("cars/{id}")
    suspend fun getCar(
        @Path("id") id: String,
        @Header("Authorization") token: String
    ): RemoteCar

    @POST("cars")
    suspend fun createCar(
        @Body car: RemoteCar,
        @Header("Authorization") token: String
    )

    @PATCH("cars/{id}")
    suspend fun updateCar(
        @Path("id") id: String,
        @Body car: RemoteCar,
        @Header("Authorization") token: String
    )

    @DELETE("cars/{id}")
    suspend fun deleteCar(@Path("id") id: String, @Header("Authorization") token: String)
}
