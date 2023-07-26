package com.uryonym.ynymportal.data

import com.uryonym.ynymportal.data.local.LocalTask
import com.uryonym.ynymportal.data.network.NetworkTask

fun LocalTask.toCommon() = Task(
    id = this.id,
    title = this.title,
    description = this.description,
    deadLine = this.deadLine,
    isComplete = this.isComplete,
    createdAt = this.createdAt,
    updatedAt = this.updatedAt
)

@JvmName("localCommon")
fun List<LocalTask>.toCommon() = map {
    it.toCommon()
}

fun NetworkTask.toCommon() = Task(
    id = this.id,
    title = this.title,
    description = this.description,
    deadLine = this.deadLine,
    isComplete = this.isComplete,
    createdAt = this.createdAt,
    updatedAt = this.updatedAt
)

@JvmName("networkLocal")
fun NetworkTask.toLocal() = this.toCommon().toLocal()

@JvmName("networkCommon")
fun List<NetworkTask>.toCommon() = map {
    it.toCommon()
}

fun Task.toLocal() = LocalTask(
    id = this.id ?: "",
    title = this.title,
    description = this.description,
    deadLine = this.deadLine,
    isComplete = this.isComplete,
    createdAt = this.createdAt,
    updatedAt = this.updatedAt
)

fun Task.toNetwork() = NetworkTask(
    id = this.id ?: "",
    title = this.title,
    description = this.description,
    deadLine = this.deadLine,
    isComplete = this.isComplete,
    createdAt = this.createdAt,
    updatedAt = this.updatedAt
)
