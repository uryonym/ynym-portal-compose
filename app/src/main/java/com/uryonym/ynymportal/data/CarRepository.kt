package com.uryonym.ynymportal.data

import com.uryonym.ynymportal.data.local.CarDao
import com.uryonym.ynymportal.data.model.Car
import com.uryonym.ynymportal.data.network.YnymPortalApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

interface CarRepository {
    fun getCars(): Flow<List<Car>>

    suspend fun getCar(id: String): Car

    suspend fun insertCar(
        name: String,
        maker: String,
        model: String,
        modelYear: Int,
        licensePlate: String,
        tankCapacity: Int
    )

    suspend fun updateCar(
        id: String,
        name: String,
        maker: String,
        model: String,
        modelYear: Int,
        licensePlate: String,
        tankCapacity: Int
    )

    suspend fun deleteCar(id: String)

    suspend fun refreshCars()

    suspend fun refreshCar(id: String): Car
}

@Singleton
class CarRepositoryImpl @Inject constructor(
    private val localDataSource: CarDao,
    private val authRepository: AuthRepository
) : CarRepository {

    override fun getCars(): Flow<List<Car>> {
        return localDataSource.getCars()
    }

    override suspend fun getCar(id: String): Car {
        return localDataSource.getCar(id)
    }

    override suspend fun insertCar(
        name: String,
        maker: String,
        model: String,
        modelYear: Int,
        licensePlate: String,
        tankCapacity: Int
    ) {
        val car = Car(
            id = "",
            name = name,
            maker = maker,
            model = model,
            modelYear = modelYear,
            licensePlate = licensePlate,
            tankCapacity = tankCapacity
        )
        val token = authRepository.getIdToken()
        val networkCar = YnymPortalApi.retrofitService.addCar(car.toNetwork(), "Bearer $token")

        localDataSource.insertCar(networkCar.toLocal())
    }

    override suspend fun updateCar(
        id: String,
        name: String,
        maker: String,
        model: String,
        modelYear: Int,
        licensePlate: String,
        tankCapacity: Int
    ) {
        val car = Car(
            id = "",
            name = name,
            maker = maker,
            model = model,
            modelYear = modelYear,
            licensePlate = licensePlate,
            tankCapacity = tankCapacity
        )
        val token = authRepository.getIdToken()
        val networkCar = YnymPortalApi.retrofitService.editCar(
            id,
            car.toNetwork(),
            "Bearer $token"
        )

        localDataSource.updateCar(networkCar.toLocal())
    }

    override suspend fun deleteCar(id: String) {
        val token = authRepository.getIdToken()
        YnymPortalApi.retrofitService.deleteCar(id, token = "Bearer $token")

        localDataSource.deleteCar(id)
    }

    override suspend fun refreshCars() {
        val token = authRepository.getIdToken()
        val cars = YnymPortalApi.retrofitService.getCars(token).toLocal()
        localDataSource.deleteAllCar()
        cars.map {
            localDataSource.insertCar(it)
        }
    }

    override suspend fun refreshCar(id: String): Car {
        val token = authRepository.getIdToken()
        val car = YnymPortalApi.retrofitService.getCar(id, token = "Bearer $token").toLocal()
        localDataSource.insertCar(car)

        return car
    }

}
