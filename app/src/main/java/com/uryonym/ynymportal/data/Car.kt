package com.uryonym.ynymportal.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Car(
    val id: String? = null,
    var name: String = "",
    var maker: String = "",
    var model: String = "",
    @SerialName(value = "model_year") var modelYear: Int = 0,
    @SerialName(value = "license_plate") var licensePlate: String? = null,
    @SerialName(value = "tank_capacity") var tankCapacity: Int? = null,
    @SerialName(value = "created_at") val createdAt: String? = null,
    @SerialName(value = "updated_at") val updatedAt: String? = null,
)