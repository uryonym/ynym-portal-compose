package com.uryonym.ynymportal.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.datetime.Instant

@Entity(tableName = "refueling")
data class Refueling(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "refuel_datetime") val refuelDateTime: Instant,
    var odometer: Int,
    @ColumnInfo(name = "fuel_type") var fuelType: String,
    var price: Int,
    var quantity: Int,
    @ColumnInfo(name = "full_flag") var fullFlag: Boolean = true,
    @ColumnInfo(name = "gas_stand") var gasStand: String = "",
    @ColumnInfo(name = "car_id") var carId: String,
    @ColumnInfo(name = "created_at") val createdAt: Instant? = null,
    @ColumnInfo(name = "updated_at") val updatedAt: Instant? = null,
)
