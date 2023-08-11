package com.uryonym.ynymportal.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.uryonym.ynymportal.data.model.Car
import kotlinx.coroutines.flow.Flow

@Dao
interface CarDao {
    @Query("SELECT * FROM car ORDER BY created_at")
    fun getCars(): Flow<List<Car>>

    @Query("SELECT * FROM car WHERE id = (:carId)")
    suspend fun getCar(carId: String): Car

    @Insert
    suspend fun insertCar(car: Car)

    @Update
    suspend fun updateCar(car: Car)

    @Query("DELETE FROM car WHERE id = (:carId)")
    suspend fun deleteCar(carId: String)

    @Query("DELETE FROM car")
    suspend fun deleteAllCar()
}
