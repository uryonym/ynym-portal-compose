package com.uryonym.ynymportal.data

import com.uryonym.ynymportal.data.local.RefuelingLocalDataSource
import com.uryonym.ynymportal.data.model.Refueling
import com.uryonym.ynymportal.data.remote.RefuelingRemoteDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

interface RefuelingRepository {
    fun getRefuelings(): Flow<List<Refueling>>

    suspend fun getRefueling(id: String): Refueling

    suspend fun insertRefueling(refueling: Refueling)

    suspend fun updateRefueling(refueling: Refueling)

    suspend fun deleteRefueling(id: String)

    suspend fun refreshRefuelings()
}

@Singleton
class RefuelingRepositoryImpl @Inject constructor(
    private val refuelingLocalDataSource: RefuelingLocalDataSource,
    private val refuelingRemoteDataSource: RefuelingRemoteDataSource
) : RefuelingRepository {
    override fun getRefuelings(): Flow<List<Refueling>> {
        return refuelingLocalDataSource.fetchRefuelings()
    }

    override suspend fun getRefueling(id: String): Refueling {
        return refuelingLocalDataSource.getRefueling(id)
    }

    override suspend fun insertRefueling(refueling: Refueling) {
        refuelingRemoteDataSource.createRefueling(refueling)
        refuelingLocalDataSource.upsertRefueling(refueling)
    }

    override suspend fun updateRefueling(refueling: Refueling) {
        refuelingRemoteDataSource.updateRefueling(refueling)
        refuelingLocalDataSource.upsertRefueling(refueling)
    }

    override suspend fun deleteRefueling(id: String) {
        refuelingRemoteDataSource.deleteRefueling(id)
        refuelingLocalDataSource.deleteRefuelingsById(listOf(id))
    }

    override suspend fun refreshRefuelings() {
        val localRefuelings = refuelingLocalDataSource.getRefuelings()
        val remoteRefuelings = refuelingRemoteDataSource.getRefuelings()
        val localIds = localRefuelings.map { it.id }.toSet()
        val remoteIds = remoteRefuelings.map { it.id }.toSet()
        val deleteIds = localIds.subtract(remoteIds).toList()

        refuelingLocalDataSource.upsertRefuelings(remoteRefuelings)
        refuelingLocalDataSource.deleteRefuelingsById(deleteIds)
    }
}
