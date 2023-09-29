package com.uryonym.ynymportal.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.uryonym.ynymportal.data.model.LocalCar
import kotlinx.coroutines.flow.Flow

@Dao
interface CarDao {
    @Query("SELECT * FROM car ORDER BY name")
    fun fetchCars(): Flow<List<LocalCar>>

    @Query("SELECT * FROM car ORDER BY name")
    suspend fun getCars(): List<LocalCar>

    @Query("SELECT * FROM car WHERE id = (:id)")
    suspend fun getCar(id: String): LocalCar

    @Upsert
    suspend fun upsertCar(car: LocalCar)

    @Upsert
    suspend fun upsertCars(cars: List<LocalCar>)

    @Query("DELETE FROM car WHERE id = (:id)")
    suspend fun deleteCarById(id: String)
}

