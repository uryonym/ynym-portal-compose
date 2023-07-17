package com.uryonym.ynymportal.data

import com.uryonym.ynymportal.data.network.YnymPortalApi

interface CarRepository {
    suspend fun getCars(token: String): List<Car>

    suspend fun getCar(id: String, token: String): Car

    suspend fun addCar(car: Car, token: String): Car

    suspend fun editCar(id: String, car: Car, token: String): Car

    suspend fun deleteCar(id: String, token: String)
}

class DefaultCarRepository : CarRepository {
    override suspend fun getCars(token: String): List<Car> {
        return YnymPortalApi.retrofitService.getCars(token = "Bearer $token")
    }

    override suspend fun getCar(id: String, token: String): Car {
        return YnymPortalApi.retrofitService.getCar(id, token = "Bearer $token")
    }

    override suspend fun addCar(car: Car, token: String): Car {
        return YnymPortalApi.retrofitService.addCar(car, token = "Bearer $token")
    }

    override suspend fun editCar(id: String, car: Car, token: String): Car {
        return YnymPortalApi.retrofitService.editCar(id, car, token = "Bearer $token")
    }

    override suspend fun deleteCar(id: String, token: String) {
        YnymPortalApi.retrofitService.deleteCar(id, token = "Bearer $token")
    }
}