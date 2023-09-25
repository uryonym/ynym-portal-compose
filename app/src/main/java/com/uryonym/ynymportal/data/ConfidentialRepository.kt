package com.uryonym.ynymportal.data

import com.uryonym.ynymportal.data.local.ConfidentialLocalDataSource
import com.uryonym.ynymportal.data.model.Confidential
import com.uryonym.ynymportal.data.remote.ConfidentialRemoteDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

interface ConfidentialRepository {
    fun getConfidentials(): Flow<List<Confidential>>

    suspend fun getConfidential(id: String): Confidential

    suspend fun insertConfidential(confidential: Confidential)

    suspend fun updateConfidential(confidential: Confidential)

    suspend fun deleteConfidential(id: String)

    suspend fun refreshConfidentials()
}

@Singleton
class ConfidentialRepositoryImpl @Inject constructor(
    private val confidentialLocalDataSource: ConfidentialLocalDataSource,
    private val confidentialRemoteDataSource: ConfidentialRemoteDataSource
) : ConfidentialRepository {
    override fun getConfidentials(): Flow<List<Confidential>> {
        return confidentialLocalDataSource.fetchConfidentials()
    }

    override suspend fun getConfidential(id: String): Confidential {
        return confidentialLocalDataSource.getConfidential(id)
    }

    override suspend fun insertConfidential(confidential: Confidential) {
        confidentialRemoteDataSource.createConfidential(confidential)
        confidentialLocalDataSource.upsertConfidential(confidential)
    }

    override suspend fun updateConfidential(confidential: Confidential) {
        confidentialRemoteDataSource.updateConfidential(confidential)
        confidentialLocalDataSource.upsertConfidential(confidential)
    }

    override suspend fun deleteConfidential(id: String) {
        confidentialRemoteDataSource.deleteConfidential(id)
        confidentialLocalDataSource.deleteConfidentialsById(listOf(id))
    }

    override suspend fun refreshConfidentials() {
        val localConfidentials = confidentialLocalDataSource.getConfidentials()
        val remoteConfidentials = confidentialRemoteDataSource.getConfidentials()
        val localIds = localConfidentials.map { it.id }.toSet()
        val remoteIds = remoteConfidentials.map { it.id }.toSet()
        val deleteIds = localIds.subtract(remoteIds).toList()

        confidentialLocalDataSource.upsertConfidentials(remoteConfidentials)
        confidentialLocalDataSource.deleteConfidentialsById(deleteIds)
    }
}
