package com.uryonym.ynymportal.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Task(
    val id: String? = null,
    var title: String = "",
    var description: String? = null,
    @SerialName(value = "dead_line") var deadLine: String? = null,
    @SerialName(value = "is_complete") var isComplete: Boolean,
    @SerialName(value = "created_at") val createdAt: String? = null,
    @SerialName(value = "updated_at") val updatedAt: String? = null,
)