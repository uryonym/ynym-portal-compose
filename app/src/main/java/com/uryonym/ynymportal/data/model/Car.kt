package com.uryonym.ynymportal.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.UUID

data class Car(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val maker: String,
    val model: String,
    val modelYear: Int,
    val licensePlate: String,
    val tankCapacity: Int
)

@Entity(tableName = "car")
data class LocalCar(
    @PrimaryKey val id: String,
    val name: String,
    val maker: String,
    val model: String,
    @ColumnInfo(name = "model_year") val modelYear: Int,
    @ColumnInfo(name = "license_plate") val licensePlate: String,
    @ColumnInfo(name = "tank_capacity") val tankCapacity: Int
)

@Serializable
data class RemoteCar(
    val id: String,
    val name: String,
    val maker: String,
    val model: String,
    @SerialName(value = "model_year") val modelYear: Int,
    @SerialName(value = "license_plate") val licensePlate: String,
    @SerialName(value = "tank_capacity") val tankCapacity: Int
)

fun Car.toLocal() = LocalCar(
    id = this.id,
    name = this.name,
    maker = this.maker,
    model = this.model,
    modelYear = this.modelYear,
    licensePlate = this.licensePlate,
    tankCapacity = this.tankCapacity
)

fun Car.toRemote() = RemoteCar(
    id = this.id,
    name = this.name,
    maker = this.maker,
    model = this.model,
    modelYear = this.modelYear,
    licensePlate = this.licensePlate,
    tankCapacity = this.tankCapacity
)

fun LocalCar.toModel() = Car(
    id = this.id,
    name = this.name,
    maker = this.maker,
    model = this.model,
    modelYear = this.modelYear,
    licensePlate = this.licensePlate,
    tankCapacity = this.tankCapacity
)

fun RemoteCar.toModel() = Car(
    id = this.id,
    name = this.name,
    maker = this.maker,
    model = this.model,
    modelYear = this.modelYear,
    licensePlate = this.licensePlate,
    tankCapacity = this.tankCapacity
)

fun List<Car>.toLocal() = map {
    it.toLocal()
}

fun List<Car>.toRemote() = map {
    it.toRemote()
}

@JvmName("listLocalCarModel")
fun List<LocalCar>.toModel() = map {
    it.toModel()
}

@JvmName("listRemoteCarModel")
fun List<RemoteCar>.toModel() = map {
    it.toModel()
}
