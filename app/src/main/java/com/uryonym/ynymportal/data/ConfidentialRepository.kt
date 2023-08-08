package com.uryonym.ynymportal.data

import com.uryonym.ynymportal.data.local.ConfidentialDao
import com.uryonym.ynymportal.data.model.Confidential
import com.uryonym.ynymportal.data.network.YnymPortalApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

interface ConfidentialRepository {
    fun getConfidentials(): Flow<List<Confidential>>

    suspend fun getConfidential(id: String): Confidential

    suspend fun insertConfidential(
        serviceName: String,
        loginId: String,
        password: String,
        other: String
    )

    suspend fun updateConfidential(
        id: String,
        serviceName: String,
        loginId: String,
        password: String,
        other: String
    )

    suspend fun deleteConfidential(id: String)

    suspend fun refreshConfidentials()

    suspend fun refreshConfidential(id: String): Confidential
}

@Singleton
class ConfidentialRepositoryImpl @Inject constructor(
    private val localDataSource: ConfidentialDao,
    private val authRepository: AuthRepository
) : ConfidentialRepository {

    override fun getConfidentials(): Flow<List<Confidential>> {
        return localDataSource.getConfidentials()
    }

    override suspend fun getConfidential(id: String): Confidential {
        return localDataSource.getConfidential(id)
    }

    override suspend fun insertConfidential(
        serviceName: String,
        loginId: String,
        password: String,
        other: String
    ) {
        val confidential = Confidential(
            id = "",
            serviceName = serviceName,
            loginId = loginId,
            password = password,
            other = other
        )
        val token = authRepository.getIdToken()
        val networkConfidential =
            YnymPortalApi.retrofitService.addConfidential(confidential.toNetwork(), "Bearer $token")

        localDataSource.insertConfidential(networkConfidential.toLocal())
    }

    override suspend fun updateConfidential(
        id: String,
        serviceName: String,
        loginId: String,
        password: String,
        other: String
    ) {
        val confidential = Confidential(
            id = id,
            serviceName = serviceName,
            loginId = loginId,
            password = password,
            other = other
        )
        val token = authRepository.getIdToken()
        val networkConfidential = YnymPortalApi.retrofitService.editConfidential(
            id,
            confidential.toNetwork(),
            "Bearer $token"
        )

        localDataSource.updateConfidential(networkConfidential.toLocal())
    }

    override suspend fun deleteConfidential(id: String) {
        val token = authRepository.getIdToken()
        YnymPortalApi.retrofitService.deleteConfidential(id, token = "Bearer $token")

        localDataSource.deleteConfidential(id)
    }

    override suspend fun refreshConfidentials() {
        val token = authRepository.getIdToken()
        val confidentials = YnymPortalApi.retrofitService.getConfidentials(token).toLocal()
        localDataSource.deleteAllConfidential()
        confidentials.map {
            localDataSource.insertConfidential(it)
        }
    }

    override suspend fun refreshConfidential(id: String): Confidential {
        val token = authRepository.getIdToken()
        val confidential =
            YnymPortalApi.retrofitService.getConfidential(id, token = "Bearer $token").toLocal()
        localDataSource.insertConfidential(confidential)

        return confidential
    }

}