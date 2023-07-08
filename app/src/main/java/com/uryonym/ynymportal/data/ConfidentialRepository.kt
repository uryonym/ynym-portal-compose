package com.uryonym.ynymportal.data

import com.uryonym.ynymportal.data.network.YnymPortalApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface ConfidentialRepository {
    fun getConfidentials(): Flow<List<Confidential>>

    suspend fun getConfidential(id: String): Confidential

    suspend fun addConfidential(confidential: Confidential): Confidential

    suspend fun editConfidential(id: String, confidential: Confidential): Confidential

    suspend fun deleteConfidential(id: String)
}

class DefaultConfidentialRepository : ConfidentialRepository {
    override fun getConfidentials(): Flow<List<Confidential>> = flow {
        emit(YnymPortalApi.retrofitService.getConfidentials())
    }

    override suspend fun getConfidential(id: String): Confidential {
        return YnymPortalApi.retrofitService.getConfidential(id)
    }

    override suspend fun addConfidential(confidential: Confidential): Confidential {
        return YnymPortalApi.retrofitService.addConfidential(confidential)
    }

    override suspend fun editConfidential(id: String, confidential: Confidential): Confidential {
        return YnymPortalApi.retrofitService.editConfidential(id, confidential)
    }

    override suspend fun deleteConfidential(id: String) {
        YnymPortalApi.retrofitService.deleteConfidential(id)
    }
}