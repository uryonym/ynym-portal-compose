package com.uryonym.ynymportal.data

import com.uryonym.ynymportal.data.local.CarLocalDataSource
import com.uryonym.ynymportal.data.model.Car
import com.uryonym.ynymportal.data.remote.CarRemoteDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

interface CarRepository {
    fun getCars(): Flow<List<Car>>

    suspend fun getCar(id: String): Car

    suspend fun insertCar(car: Car)

    suspend fun updateCar(car: Car)

    suspend fun deleteCar(id: String)

    suspend fun refreshCars()
}

@Singleton
class CarRepositoryImpl @Inject constructor(
    private val carLocalDataSource: CarLocalDataSource,
    private val carRemoteDataSource: CarRemoteDataSource
) : CarRepository {
    override fun getCars(): Flow<List<Car>> {
        return carLocalDataSource.fetchCars()
    }

    override suspend fun getCar(id: String): Car {
        return carLocalDataSource.getCar(id)
    }

    override suspend fun insertCar(car: Car) {
        carRemoteDataSource.createCar(car)
        carLocalDataSource.upsertCar(car)
    }

    override suspend fun updateCar(car: Car) {
        carRemoteDataSource.updateCar(car)
        carLocalDataSource.upsertCar(car)
    }

    override suspend fun deleteCar(id: String) {
        carRemoteDataSource.deleteCar(id)
        carLocalDataSource.deleteCarsById(listOf(id))
    }

    override suspend fun refreshCars() {
        val localCars = carLocalDataSource.getCars()
        val remoteCars = carRemoteDataSource.getCars()
        val localIds = localCars.map { it.id }.toSet()
        val remoteIds = remoteCars.map { it.id }.toSet()
        val deleteIds = localIds.subtract(remoteIds).toList()

        carLocalDataSource.upsertCars(remoteCars)
        carLocalDataSource.deleteCarsById(deleteIds)
    }
}
