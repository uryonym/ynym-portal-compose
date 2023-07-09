package com.uryonym.ynymportal.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Confidential(
    val id: String? = null,
    @SerialName(value = "service_name") var serviceName: String = "",
    @SerialName(value = "login_id") var loginId: String = "",
    var password: String? = null,
    var other: String? = null,
    @SerialName(value = "created_at") val createdAt: String? = null,
    @SerialName(value = "updated_at") val updatedAt: String? = null,
)