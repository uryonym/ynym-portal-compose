package com.uryonym.ynymportal.data.network

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkTask(
    val id: String = "",
    var title: String = "",
    var description: String? = null,
    @SerialName(value = "dead_line") var deadLine: LocalDate? = null,
    @SerialName(value = "is_complete") var isComplete: Boolean,
    @SerialName(value = "created_at") val createdAt: Instant? = null,
    @SerialName(value = "updated_at") val updatedAt: Instant? = null,
)