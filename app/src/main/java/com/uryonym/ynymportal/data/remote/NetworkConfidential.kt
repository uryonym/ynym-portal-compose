package com.uryonym.ynymportal.data.remote

import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkConfidential(
    val id: String = "",
    @SerialName(value = "service_name") var serviceName: String = "",
    @SerialName(value = "login_id") var loginId: String = "",
    var password: String? = null,
    var other: String? = null,
    @SerialName(value = "created_at") val createdAt: Instant? = null,
    @SerialName(value = "updated_at") val updatedAt: Instant? = null,
)
