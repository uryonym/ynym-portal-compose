package com.uryonym.ynymportal.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.datetime.Instant

@Entity(tableName = "car")
data class Car(
    @PrimaryKey val id: String,
    var name: String = "",
    var maker: String = "",
    var model: String = "",
    @ColumnInfo(name = "model_year") var modelYear: Int = 0,
    @ColumnInfo(name = "license_plate") var licensePlate: String? = null,
    @ColumnInfo(name = "tank_capacity") var tankCapacity: Int? = null,
    @ColumnInfo(name = "created_at") val createdAt: Instant? = null,
    @ColumnInfo(name = "updated_at") val updatedAt: Instant? = null,
)
