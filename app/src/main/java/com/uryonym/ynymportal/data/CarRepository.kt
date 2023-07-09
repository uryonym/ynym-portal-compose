package com.uryonym.ynymportal.data

import com.uryonym.ynymportal.data.network.YnymPortalApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface CarRepository {
    fun getCars(): Flow<List<Car>>

    suspend fun getCar(id: String): Car

    suspend fun addCar(car: Car): Car

    suspend fun editCar(id: String, car: Car): Car

    suspend fun deleteCar(id: String)
}

class DefaultCarRepository : CarRepository {
    override fun getCars(): Flow<List<Car>> = flow {
        emit(YnymPortalApi.retrofitService.getCars())
    }

    override suspend fun getCar(id: String): Car {
        return YnymPortalApi.retrofitService.getCar(id)
    }

    override suspend fun addCar(car: Car): Car {
        return YnymPortalApi.retrofitService.addCar(car)
    }

    override suspend fun editCar(id: String, car: Car): Car {
        return YnymPortalApi.retrofitService.editCar(id, car)
    }

    override suspend fun deleteCar(id: String) {
        YnymPortalApi.retrofitService.deleteCar(id)
    }
}