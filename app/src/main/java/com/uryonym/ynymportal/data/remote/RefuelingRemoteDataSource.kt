package com.uryonym.ynymportal.data.remote

import com.uryonym.ynymportal.data.AuthRepository
import com.uryonym.ynymportal.data.model.Refueling
import com.uryonym.ynymportal.data.model.toModel
import com.uryonym.ynymportal.data.model.toRemote
import com.uryonym.ynymportal.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

interface RefuelingRemoteDataSource {
    fun fetchRefuelings(): Flow<List<Refueling>>

    suspend fun getRefuelings(): List<Refueling>

    suspend fun getRefueling(id: String): Refueling

    suspend fun createRefueling(refueling: Refueling)

    suspend fun updateRefueling(refueling: Refueling)

    suspend fun deleteRefueling(id: String)
}

@Singleton
class RefuelingRemoteDataSourceImpl @Inject constructor(
    private val authRepository: AuthRepository,
    private val refuelingApiService: RefuelingApiService,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : RefuelingRemoteDataSource {
    override fun fetchRefuelings(): Flow<List<Refueling>> = flow {
        val token = authRepository.getIdToken()
        withContext(ioDispatcher) {
            emit(refuelingApiService.getRefuelings(token).toModel())
        }
    }

    override suspend fun getRefuelings(): List<Refueling> {
        val token = authRepository.getIdToken()
        return withContext(ioDispatcher) {
            refuelingApiService.getRefuelings(token).toModel()
        }
    }

    override suspend fun getRefueling(id: String): Refueling {
        val token = authRepository.getIdToken()
        return withContext(ioDispatcher) {
            refuelingApiService.getRefueling(id, token).toModel()
        }
    }

    override suspend fun createRefueling(refueling: Refueling) {
        val token = authRepository.getIdToken()
        withContext(ioDispatcher) {
            refuelingApiService.createRefueling(refueling.toRemote(), token)
        }
    }

    override suspend fun updateRefueling(refueling: Refueling) {
        val token = authRepository.getIdToken()
        withContext(ioDispatcher) {
            refuelingApiService.updateRefueling(refueling.id, refueling.toRemote(), token)
        }
    }

    override suspend fun deleteRefueling(id: String) {
        val token = authRepository.getIdToken()
        withContext(ioDispatcher) {
            refuelingApiService.deleteRefueling(id, token)
        }
    }
}
