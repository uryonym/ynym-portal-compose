package com.uryonym.ynymportal.data.remote

import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkRefueling(
    val id: String = "",
    @SerialName(value = "refuel_datetime") val refuelDateTime: Instant,
    var odometer: Int,
    @SerialName(value = "fuel_type") var fuelType: String,
    var price: Int,
    @SerialName(value = "total_cost") var totalCost: Int,
    @SerialName(value = "full_flag") var fullFlag: Boolean = true,
    @SerialName(value = "gas_stand") var gasStand: String = "",
    @SerialName(value = "car_id") var carId: String,
    @SerialName(value = "created_at") val createdAt: Instant? = null,
    @SerialName(value = "updated_at") val updatedAt: Instant? = null,
)
