package com.uryonym.ynymportal.data.local

import com.uryonym.ynymportal.data.model.Refueling
import com.uryonym.ynymportal.data.model.toLocal
import com.uryonym.ynymportal.data.model.toModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

interface RefuelingLocalDataSource {
    fun fetchRefuelings(): Flow<List<Refueling>>

    suspend fun getRefuelings(): List<Refueling>

    suspend fun getRefueling(id: String): Refueling

    suspend fun upsertRefueling(refueling: Refueling)

    suspend fun upsertRefuelings(refuelings: List<Refueling>)

    suspend fun deleteRefuelingsById(ids: List<String>)
}

@Singleton
class RefuelingLocalDataSourceImpl @Inject constructor(
    private val refuelingDao: RefuelingDao
) : RefuelingLocalDataSource {
    override fun fetchRefuelings(): Flow<List<Refueling>> {
        return refuelingDao.fetchRefuelings().map { it.toModel() }
    }

    override suspend fun getRefuelings(): List<Refueling> {
        return refuelingDao.getRefuelings().toModel()
    }

    override suspend fun getRefueling(id: String): Refueling {
        return refuelingDao.getRefueling(id).toModel()
    }

    override suspend fun upsertRefueling(refueling: Refueling) {
        refuelingDao.upsertRefueling(refueling.toLocal())
    }

    override suspend fun upsertRefuelings(refuelings: List<Refueling>) {
        refuelingDao.upsertRefuelings(refuelings.toLocal())
    }

    override suspend fun deleteRefuelingsById(ids: List<String>) {
        ids.forEach { refuelingDao.deleteRefuelingById(it) }
    }
}
