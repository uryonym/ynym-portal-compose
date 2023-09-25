package com.uryonym.ynymportal.data.local

import com.uryonym.ynymportal.data.model.Confidential
import com.uryonym.ynymportal.data.model.toLocal
import com.uryonym.ynymportal.data.model.toModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

interface ConfidentialLocalDataSource {
    fun fetchConfidentials(): Flow<List<Confidential>>

    suspend fun getConfidentials(): List<Confidential>

    suspend fun getConfidential(id: String): Confidential

    suspend fun upsertConfidential(confidential: Confidential)

    suspend fun upsertConfidentials(confidentials: List<Confidential>)

    suspend fun deleteConfidentialsById(ids: List<String>)
}

@Singleton
class ConfidentialLocalDataSourceImpl @Inject constructor(
    private val confidentialDao: ConfidentialDao
) : ConfidentialLocalDataSource {
    override fun fetchConfidentials(): Flow<List<Confidential>> {
        return confidentialDao.fetchConfidentials().map { it.toModel() }
    }

    override suspend fun getConfidentials(): List<Confidential> {
        return confidentialDao.getConfidentials().toModel()
    }

    override suspend fun getConfidential(id: String): Confidential {
        return confidentialDao.getConfidential(id).toModel()
    }


    override suspend fun upsertConfidential(confidential: Confidential) {
        confidentialDao.upsertConfidential(confidential.toLocal())
    }

    override suspend fun upsertConfidentials(confidentials: List<Confidential>) {
        confidentialDao.upsertConfidentials(confidentials.toLocal())
    }

    override suspend fun deleteConfidentialsById(ids: List<String>) {
        ids.forEach { confidentialDao.deleteConfidentialById(it) }
    }
}
