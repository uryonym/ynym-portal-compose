package com.uryonym.ynymportal.data.network

import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkTaskList(
    val id: String = "",
    var name: String = "",
    val uid: String = "",
    var seq: Int? = null,
    @SerialName(value = "created_at") val createdAt: Instant? = null,
    @SerialName(value = "updated_at") val updatedAt: Instant? = null,
)
