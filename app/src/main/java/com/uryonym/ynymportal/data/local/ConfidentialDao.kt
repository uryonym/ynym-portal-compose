package com.uryonym.ynymportal.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.uryonym.ynymportal.data.model.LocalConfidential
import kotlinx.coroutines.flow.Flow

@Dao
interface ConfidentialDao {
    @Query("SELECT * FROM confidential ORDER BY service_name")
    fun fetchConfidentials(): Flow<List<LocalConfidential>>

    @Query("SELECT * FROM confidential ORDER BY service_name")
    suspend fun getConfidentials(): List<LocalConfidential>

    @Query("SELECT * FROM confidential WHERE id = (:id)")
    suspend fun getConfidential(id: String): LocalConfidential

    @Upsert
    suspend fun upsertConfidential(confidential: LocalConfidential)

    @Upsert
    suspend fun upsertConfidentials(confidentials: List<LocalConfidential>)

    @Query("DELETE FROM confidential WHERE id = (:id)")
    suspend fun deleteConfidentialById(id: String)
}
