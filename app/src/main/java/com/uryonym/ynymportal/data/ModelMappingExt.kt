package com.uryonym.ynymportal.data

import com.uryonym.ynymportal.data.model.Refueling
import com.uryonym.ynymportal.data.remote.NetworkRefueling

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
