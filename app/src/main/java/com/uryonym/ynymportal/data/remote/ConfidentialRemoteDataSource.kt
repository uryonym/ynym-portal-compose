package com.uryonym.ynymportal.data.remote

import com.uryonym.ynymportal.data.AuthRepository
import com.uryonym.ynymportal.data.model.Confidential
import com.uryonym.ynymportal.data.model.RemoteConfidential
import com.uryonym.ynymportal.data.model.toModel
import com.uryonym.ynymportal.data.model.toRemote
import com.uryonym.ynymportal.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

interface ConfidentialRemoteDataSource {
    fun fetchConfidentials(): Flow<List<Confidential>>

    suspend fun getConfidentials(): List<Confidential>

    suspend fun getConfidential(id: String): RemoteConfidential

    suspend fun createConfidential(confidential: Confidential)

    suspend fun updateConfidential(confidential: Confidential)

    suspend fun deleteConfidential(id: String)
}

@Singleton
class ConfidentialRemoteDataSourceImpl @Inject constructor(
    private val authRepository: AuthRepository,
    private val confidentialApiService: ConfidentialApiService,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ConfidentialRemoteDataSource {
    override fun fetchConfidentials(): Flow<List<Confidential>> = flow {
        val token = authRepository.getIdToken()
        withContext(ioDispatcher) {
            emit(confidentialApiService.getConfidentials(token).toModel())
        }
    }

    override suspend fun getConfidentials(): List<Confidential> {
        val token = authRepository.getIdToken()
        return withContext(ioDispatcher) {
            confidentialApiService.getConfidentials(token).toModel()
        }
    }

    override suspend fun getConfidential(id: String): RemoteConfidential {
        val token = authRepository.getIdToken()
        return withContext(ioDispatcher) {
            confidentialApiService.getConfidential(id, token)
        }
    }

    override suspend fun createConfidential(confidential: Confidential) {
        val token = authRepository.getIdToken()
        withContext(ioDispatcher) {
            confidentialApiService.createConfidential(confidential.toRemote(), token)
        }
    }

    override suspend fun updateConfidential(confidential: Confidential) {
        val token = authRepository.getIdToken()
        withContext(ioDispatcher) {
            confidentialApiService.updateConfidential(
                confidential.id,
                confidential.toRemote(),
                token
            )
        }
    }

    override suspend fun deleteConfidential(id: String) {
        val token = authRepository.getIdToken()
        withContext(ioDispatcher) {
            confidentialApiService.deleteConfidential(id, token)
        }
    }
}
