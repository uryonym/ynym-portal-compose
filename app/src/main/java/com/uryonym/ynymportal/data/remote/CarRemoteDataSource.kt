package com.uryonym.ynymportal.data.remote

import com.uryonym.ynymportal.data.AuthRepository
import com.uryonym.ynymportal.data.model.Car
import com.uryonym.ynymportal.data.model.toModel
import com.uryonym.ynymportal.data.model.toRemote
import com.uryonym.ynymportal.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

interface CarRemoteDataSource {
    fun fetchCars(): Flow<List<Car>>

    suspend fun getCars(): List<Car>

    suspend fun getCar(id: String): Car

    suspend fun createCar(car: Car)

    suspend fun updateCar(car: Car)

    suspend fun deleteCar(id: String)
}

@Singleton
class CarRemoteDataSourceImpl @Inject constructor(
    private val authRepository: AuthRepository,
    private val carApiService: CarApiService,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : CarRemoteDataSource {
    override fun fetchCars(): Flow<List<Car>> = flow {
        val token = authRepository.getIdToken()
        withContext(ioDispatcher) {
            emit(carApiService.getCars(token).toModel())
        }
    }

    override suspend fun getCars(): List<Car> {
        val token = authRepository.getIdToken()
        return withContext(ioDispatcher) {
            carApiService.getCars(token).toModel()
        }
    }

    override suspend fun getCar(id: String): Car {
        val token = authRepository.getIdToken()
        return withContext(ioDispatcher) {
            carApiService.getCar(id, token).toModel()
        }
    }

    override suspend fun createCar(car: Car) {
        val token = authRepository.getIdToken()
        withContext(ioDispatcher) {
            carApiService.createCar(car.toRemote(), token)
        }
    }

    override suspend fun updateCar(car: Car) {
        val token = authRepository.getIdToken()
        withContext(ioDispatcher) {
            carApiService.updateCar(
                car.id,
                car.toRemote(),
                token
            )
        }
    }

    override suspend fun deleteCar(id: String) {
        val token = authRepository.getIdToken()
        withContext(ioDispatcher) {
            carApiService.deleteCar(id, token)
        }
    }
}
