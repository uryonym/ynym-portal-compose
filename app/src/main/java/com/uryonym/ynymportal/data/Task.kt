package com.uryonym.ynymportal.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Task(
    val id: String,
    val title: String,
    val description: String?,
    @SerialName(value = "dead_line") val deadLine: String?,
    @SerialName(value = "is_complete") val isComplete: Boolean,
    @SerialName(value = "created_at") val createdAt: String?,
    @SerialName(value = "updated_at") val updatedAt: String?,
)