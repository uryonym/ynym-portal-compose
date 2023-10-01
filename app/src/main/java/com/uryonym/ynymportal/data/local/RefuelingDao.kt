package com.uryonym.ynymportal.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.uryonym.ynymportal.data.model.LocalRefueling
import kotlinx.coroutines.flow.Flow

@Dao
interface RefuelingDao {
    @Query("SELECT * FROM refueling ORDER BY refuel_datetime DESC")
    fun fetchRefuelings(): Flow<List<LocalRefueling>>

    @Query("SELECT * FROM refueling ORDER BY refuel_datetime DESC")
    suspend fun getRefuelings(): List<LocalRefueling>

    @Query("SELECT * FROM refueling WHERE id = (:id)")
    suspend fun getRefueling(id: String): LocalRefueling

    @Upsert
    suspend fun upsertRefueling(refueling: LocalRefueling)

    @Upsert
    suspend fun upsertRefuelings(refuelings: List<LocalRefueling>)

    @Query("DELETE FROM refueling WHERE id = (:id)")
    suspend fun deleteRefuelingById(id: String)
}
