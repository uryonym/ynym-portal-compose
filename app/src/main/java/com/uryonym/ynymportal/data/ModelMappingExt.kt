package com.uryonym.ynymportal.data

import com.uryonym.ynymportal.data.model.Car
import com.uryonym.ynymportal.data.model.Refueling
import com.uryonym.ynymportal.data.remote.NetworkCar
import com.uryonym.ynymportal.data.remote.NetworkRefueling

fun Car.toNetwork() = NetworkCar(
    id = this.id,
    name = this.name,
    maker = this.maker,
    model = this.model,
    modelYear = this.modelYear,
    licensePlate = this.licensePlate,
    tankCapacity = this.tankCapacity,
    createdAt = this.createdAt,
    updatedAt = this.updatedAt
)

@JvmName("listCarToNetwork")
fun List<Car>.toNetwork() = map {
    it.toNetwork()
}

fun NetworkCar.toLocal() = Car(
    id = this.id,
    name = this.name,
    maker = this.maker,
    model = this.model,
    modelYear = this.modelYear,
    licensePlate = this.licensePlate,
    tankCapacity = this.tankCapacity,
    createdAt = this.createdAt,
    updatedAt = this.updatedAt
)

@JvmName("listCarToLocal")
fun List<NetworkCar>.toLocal() = map {
    it.toLocal()
}

fun Refueling.toNetwork() = NetworkRefueling(
    id = this.id,
    refuelDateTime = this.refuelDateTime,
    odometer = this.odometer,
    fuelType = this.fuelType,
    price = this.price,
    totalCost = this.totalCost,
    fullFlag = this.fullFlag,
    gasStand = this.gasStand,
    carId = this.carId,
    createdAt = this.createdAt,
    updatedAt = this.updatedAt
)

@JvmName("listRefuelingToNetwork")
fun List<Refueling>.toNetwork() = map {
    it.toNetwork()
}

fun NetworkRefueling.toLocal() = Refueling(
    id = this.id,
    refuelDateTime = this.refuelDateTime,
    odometer = this.odometer,
    fuelType = this.fuelType,
    price = this.price,
    totalCost = this.totalCost,
    fullFlag = this.fullFlag,
    gasStand = this.gasStand,
    carId = this.carId,
    createdAt = this.createdAt,
    updatedAt = this.updatedAt
)

@JvmName("listRefuelingToLocal")
fun List<NetworkRefueling>.toLocal() = map {
    it.toLocal()
}
