package com.uryonym.ynymportal.data

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate

data class Task(
    val id: String? = null,
    var title: String = "",
    var description: String? = null,
    var deadLine: LocalDate? = null,
    var isComplete: Boolean,
    val createdAt: Instant? = null,
    val updatedAt: Instant? = null,
)
