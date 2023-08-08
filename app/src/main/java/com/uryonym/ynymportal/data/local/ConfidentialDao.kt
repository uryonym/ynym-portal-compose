package com.uryonym.ynymportal.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.uryonym.ynymportal.data.model.Confidential
import kotlinx.coroutines.flow.Flow

@Dao
interface ConfidentialDao {
    @Query("SELECT * FROM confidential ORDER BY service_name")
    fun getConfidentials(): Flow<List<Confidential>>

    @Query("SELECT * FROM confidential WHERE id = (:confidentialId)")
    suspend fun getConfidential(confidentialId: String): Confidential

    @Insert
    suspend fun insertConfidential(confidential: Confidential)

    @Update
    suspend fun updateConfidential(confidential: Confidential)

    @Query("DELETE FROM confidential WHERE id = (:confidentialId)")
    suspend fun deleteConfidential(confidentialId: String)

    @Query("DELETE FROM confidential")
    suspend fun deleteAllConfidential()
}
