package com.uryonym.ynymportal.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.UUID

data class Refueling(
    val id: String = UUID.randomUUID().toString(),
    val refuelDateTime: Instant,
    val odometer: Int,
    val fuelType: String,
    val price: Int,
    val totalCost: Int,
    val fullFlag: Boolean,
    val gasStand: String,
    val carId: String
)

@Entity(tableName = "refueling")
data class LocalRefueling(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "refuel_datetime") val refuelDateTime: Instant,
    val odometer: Int,
    @ColumnInfo(name = "fuel_type") val fuelType: String,
    val price: Int,
    @ColumnInfo(name = "total_cost") val totalCost: Int,
    @ColumnInfo(name = "full_flag") val fullFlag: Boolean = true,
    @ColumnInfo(name = "gas_stand") val gasStand: String,
    @ColumnInfo(name = "car_id") var carId: String
)

@Serializable
data class RemoteRefueling(
    val id: String,
    @SerialName(value = "refuel_datetime") val refuelDateTime: Instant,
    val odometer: Int,
    @SerialName(value = "fuel_type") val fuelType: String,
    val price: Int,
    @SerialName(value = "total_cost") val totalCost: Int,
    @SerialName(value = "full_flag") val fullFlag: Boolean = true,
    @SerialName(value = "gas_stand") val gasStand: String,
    @SerialName(value = "car_id") val carId: String
)

fun Refueling.toLocal() = LocalRefueling(
    id = this.id,
    refuelDateTime = this.refuelDateTime,
    odometer = this.odometer,
    fuelType = this.fuelType,
    price = this.price,
    totalCost = this.totalCost,
    fullFlag = this.fullFlag,
    gasStand = this.gasStand,
    carId = this.carId
)

fun Refueling.toRemote() = RemoteRefueling(
    id = this.id,
    refuelDateTime = this.refuelDateTime,
    odometer = this.odometer,
    fuelType = this.fuelType,
    price = this.price,
    totalCost = this.totalCost,
    fullFlag = this.fullFlag,
    gasStand = this.gasStand,
    carId = this.carId
)

fun LocalRefueling.toModel() = Refueling(
    id = this.id,
    refuelDateTime = this.refuelDateTime,
    odometer = this.odometer,
    fuelType = this.fuelType,
    price = this.price,
    totalCost = this.totalCost,
    fullFlag = this.fullFlag,
    gasStand = this.gasStand,
    carId = this.carId
)

fun RemoteRefueling.toModel() = Refueling(
    id = this.id,
    refuelDateTime = this.refuelDateTime,
    odometer = this.odometer,
    fuelType = this.fuelType,
    price = this.price,
    totalCost = this.totalCost,
    fullFlag = this.fullFlag,
    gasStand = this.gasStand,
    carId = this.carId
)

fun List<Refueling>.toLocal() = map {
    it.toLocal()
}

fun List<Refueling>.toRemote() = map {
    it.toRemote()
}

@JvmName("listLocalRefuelingModel")
fun List<LocalRefueling>.toModel() = map {
    it.toModel()
}

@JvmName("listRemoteRefuelingModel")
fun List<RemoteRefueling>.toModel() = map {
    it.toModel()
}
