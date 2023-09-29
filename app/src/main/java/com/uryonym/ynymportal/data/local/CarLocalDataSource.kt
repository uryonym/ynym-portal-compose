package com.uryonym.ynymportal.data.local

import com.uryonym.ynymportal.data.model.Car
import com.uryonym.ynymportal.data.model.toLocal
import com.uryonym.ynymportal.data.model.toModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

interface CarLocalDataSource {
    fun fetchCars(): Flow<List<Car>>

    suspend fun getCars(): List<Car>

    suspend fun getCar(id: String): Car

    suspend fun upsertCar(car: Car)

    suspend fun upsertCars(cars: List<Car>)

    suspend fun deleteCarsById(ids: List<String>)
}

@Singleton
class CarLocalDataSourceImpl @Inject constructor(
    private val carDao: CarDao
) : CarLocalDataSource {
    override fun fetchCars(): Flow<List<Car>> {
        return carDao.fetchCars().map { it.toModel() }
    }

    override suspend fun getCars(): List<Car> {
        return carDao.getCars().toModel()
    }

    override suspend fun getCar(id: String): Car {
        return carDao.getCar(id).toModel()
    }

    override suspend fun upsertCar(car: Car) {
        carDao.upsertCar(car.toLocal())
    }

    override suspend fun upsertCars(cars: List<Car>) {
        carDao.upsertCars(cars.toLocal())
    }

    override suspend fun deleteCarsById(ids: List<String>) {
        ids.forEach { carDao.deleteCarById(it) }
    }
}
