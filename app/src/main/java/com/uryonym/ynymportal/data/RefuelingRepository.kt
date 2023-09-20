package com.uryonym.ynymportal.data

import com.uryonym.ynymportal.data.local.RefuelingDao
import com.uryonym.ynymportal.data.model.Refueling
import com.uryonym.ynymportal.data.remote.YnymPortalApi
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.Instant
import javax.inject.Inject
import javax.inject.Singleton

interface RefuelingRepository {
    fun getRefuelings(carId: String): Flow<List<Refueling>>

    suspend fun getRefueling(id: String): Refueling

    suspend fun insertRefueling(
        refuelDateTime: Instant,
        odometer: Int,
        fuelType: String,
        price: Int,
        totalCost: Int,
        fullFlag: Boolean,
        gasStand: String,
        carId: String
    )

    suspend fun updateRefueling(
        id: String,
        refuelDateTime: Instant,
        odometer: Int,
        fuelType: String,
        price: Int,
        totalCost: Int,
        fullFlag: Boolean,
        gasStand: String,
        carId: String
    )

    suspend fun deleteRefueling(id: String)

    suspend fun refreshRefuelings()

    suspend fun refreshRefueling(id: String): Refueling
}

@Singleton
class RefuelingRepositoryImpl @Inject constructor(
    private val localDataSource: RefuelingDao,
    private val authRepository: AuthRepository
) : RefuelingRepository {

    override fun getRefuelings(carId: String): Flow<List<Refueling>> {
        return localDataSource.getRefuelings(carId)
    }

    override suspend fun getRefueling(id: String): Refueling {
        return localDataSource.getRefueling(id)
    }

    override suspend fun insertRefueling(
        refuelDateTime: Instant,
        odometer: Int,
        fuelType: String,
        price: Int,
        totalCost: Int,
        fullFlag: Boolean,
        gasStand: String,
        carId: String
    ) {
        val refueling = Refueling(
            id = "",
            refuelDateTime = refuelDateTime,
            odometer = odometer,
            fuelType = fuelType,
            price = price,
            totalCost = totalCost,
            fullFlag = fullFlag,
            gasStand = gasStand,
            carId = carId
        )
        val token = authRepository.getIdToken()
        val networkRefueling =
            YnymPortalApi.retrofitService.addRefueling(refueling.toNetwork(), "Bearer $token")

        localDataSource.insertRefueling(networkRefueling.toLocal())
    }

    override suspend fun updateRefueling(
        id: String,
        refuelDateTime: Instant,
        odometer: Int,
        fuelType: String,
        price: Int,
        totalCost: Int,
        fullFlag: Boolean,
        gasStand: String,
        carId: String
    ) {
        val refueling = Refueling(
            id = "",
            refuelDateTime = refuelDateTime,
            odometer = odometer,
            fuelType = fuelType,
            price = price,
            totalCost = totalCost,
            fullFlag = fullFlag,
            gasStand = gasStand,
            carId = carId
        )
        val token = authRepository.getIdToken()
        val networkRefueling =
            YnymPortalApi.retrofitService.editRefueling(id, refueling.toNetwork(), "Bearer $token")

        localDataSource.updateRefueling(networkRefueling.toLocal())
    }

    override suspend fun deleteRefueling(id: String) {
        val token = authRepository.getIdToken()
        YnymPortalApi.retrofitService.deleteRefueling(id, token = "Bearer $token")

        localDataSource.deleteRefueling(id)
    }

    override suspend fun refreshRefuelings() {
        val token = authRepository.getIdToken()
        val refuelings = YnymPortalApi.retrofitService.getRefuelings(token).toLocal()
        localDataSource.deleteAllRefueling()
        refuelings.map {
            localDataSource.insertRefueling(it)
        }
    }

    override suspend fun refreshRefueling(id: String): Refueling {
        val token = authRepository.getIdToken()
        val refueling =
            YnymPortalApi.retrofitService.getRefueling(id, token = "Bearer $token").toLocal()
        localDataSource.insertRefueling(refueling)

        return refueling
    }

}
