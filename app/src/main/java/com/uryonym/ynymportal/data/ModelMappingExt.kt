package com.uryonym.ynymportal.data

import com.uryonym.ynymportal.data.model.Confidential
import com.uryonym.ynymportal.data.model.Task
import com.uryonym.ynymportal.data.network.NetworkConfidential
import com.uryonym.ynymportal.data.network.NetworkTask

fun Task.toNetwork() = NetworkTask(
    id = this.id,
    title = this.title,
    description = this.description,
    deadLine = this.deadLine,
    isComplete = this.isComplete,
    createdAt = this.createdAt,
    updatedAt = this.updatedAt
)

@JvmName("listTaskToNetwork")
fun List<Task>.toNetwork() = map {
    it.toNetwork()
}

fun NetworkTask.toLocal() = Task(
    id = this.id,
    title = this.title,
    description = this.description,
    deadLine = this.deadLine,
    isComplete = this.isComplete,
    createdAt = this.createdAt,
    updatedAt = this.updatedAt
)

@JvmName("listTaskToLocal")
fun List<NetworkTask>.toLocal() = map {
    it.toLocal()
}

fun Confidential.toNetwork() = NetworkConfidential(
    id = this.id,
    serviceName = this.serviceName,
    loginId = this.loginId,
    password = this.password,
    other = this.other,
    createdAt = this.createdAt,
    updatedAt = this.updatedAt
)

@JvmName("listConfidentialToNetwork")
fun List<Confidential>.toNetwork() = map {
    it.toNetwork()
}

fun NetworkConfidential.toLocal() = Confidential(
    id = this.id,
    serviceName = this.serviceName,
    loginId = this.loginId,
    password = this.password,
    other = this.other,
    createdAt = this.createdAt,
    updatedAt = this.updatedAt
)

@JvmName("listConfidentialToLocal")
fun List<NetworkConfidential>.toLocal() = map {
    it.toLocal()
}
