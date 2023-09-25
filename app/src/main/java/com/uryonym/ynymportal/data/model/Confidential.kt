package com.uryonym.ynymportal.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.UUID

data class Confidential(
    val id: String = UUID.randomUUID().toString(),
    val serviceName: String,
    val loginId: String,
    val password: String,
    val other: String,
    val createdAt: Instant = Clock.System.now()
)

@Entity(tableName = "confidential")
data class LocalConfidential(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "service_name") val serviceName: String,
    @ColumnInfo(name = "login_id") val loginId: String,
    val password: String,
    val other: String,
    @ColumnInfo(name = "created_at") val createdAt: Instant
)

@Serializable
data class RemoteConfidential(
    val id: String,
    @SerialName(value = "service_name") val serviceName: String,
    @SerialName(value = "login_id") val loginId: String,
    val password: String,
    val other: String,
    @SerialName(value = "created_at") val createdAt: Instant
)

fun Confidential.toLocal() = LocalConfidential(
    id = this.id,
    serviceName = this.serviceName,
    loginId = this.loginId,
    password = this.password,
    other = this.other,
    createdAt = this.createdAt
)

fun Confidential.toRemote() = RemoteConfidential(
    id = this.id,
    serviceName = this.serviceName,
    loginId = this.loginId,
    password = this.password,
    other = this.other,
    createdAt = this.createdAt
)

fun LocalConfidential.toModel() = Confidential(
    id = this.id,
    serviceName = this.serviceName,
    loginId = this.loginId,
    password = this.password,
    other = this.other,
    createdAt = this.createdAt
)

fun RemoteConfidential.toModel() = Confidential(
    id = this.id,
    serviceName = this.serviceName,
    loginId = this.loginId,
    password = this.password,
    other = this.other,
    createdAt = this.createdAt
)

fun List<Confidential>.toLocal() = map {
    it.toLocal()
}

fun List<Confidential>.toRemote() = map {
    it.toRemote()
}

@JvmName("listLocalConfidentialModel")
fun List<LocalConfidential>.toModel() = map {
    it.toModel()
}

@JvmName("listRemoteConfidentialModel")
fun List<RemoteConfidential>.toModel() = map {
    it.toModel()
}
