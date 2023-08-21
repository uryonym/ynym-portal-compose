package com.uryonym.ynymportal.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.uryonym.ynymportal.data.model.Refueling
import kotlinx.coroutines.flow.Flow

@Dao
interface RefuelingDao {
    @Query("SELECT * FROM refueling WHERE car_id = (:carId) ORDER BY created_at")
    fun getRefuelings(carId: String): Flow<List<Refueling>>

    @Query("SELECT * FROM refueling WHERE id = (:refuelingId)")
    suspend fun getRefueling(refuelingId: String): Refueling

    @Insert
    suspend fun insertRefueling(refueling: Refueling)

    @Update
    suspend fun updateRefueling(refueling: Refueling)

    @Query("DELETE FROM refueling WHERE id = (:refuelingId)")
    suspend fun deleteRefueling(refuelingId: String)

    @Query("DELETE FROM refueling")
    suspend fun deleteAllRefueling()
}
