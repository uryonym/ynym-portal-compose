package com.uryonym.ynymportal.data

import com.uryonym.ynymportal.data.network.YnymPortalApi

interface ConfidentialRepository {
    suspend fun getConfidentials(token: String): List<Confidential>

    suspend fun getConfidential(id: String, token: String): Confidential

    suspend fun addConfidential(confidential: Confidential, token: String): Confidential

    suspend fun editConfidential(
        id: String,
        confidential: Confidential,
        token: String
    ): Confidential

    suspend fun deleteConfidential(id: String, token: String)
}

class DefaultConfidentialRepository : ConfidentialRepository {
    override suspend fun getConfidentials(token: String): List<Confidential> {
        return YnymPortalApi.retrofitService.getConfidentials(token = "Bearer $token")
    }

    override suspend fun getConfidential(id: String, token: String): Confidential {
        return YnymPortalApi.retrofitService.getConfidential(id, token = "Bearer $token")
    }

    override suspend fun addConfidential(confidential: Confidential, token: String): Confidential {
        return YnymPortalApi.retrofitService.addConfidential(confidential, token = "Bearer $token")
    }

    override suspend fun editConfidential(
        id: String,
        confidential: Confidential,
        token: String
    ): Confidential {
        return YnymPortalApi.retrofitService.editConfidential(
            id,
            confidential,
            token = "Bearer $token"
        )
    }

    override suspend fun deleteConfidential(id: String, token: String) {
        YnymPortalApi.retrofitService.deleteConfidential(id, token = "Bearer $token")
    }
}