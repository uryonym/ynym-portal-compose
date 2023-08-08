package com.uryonym.ynymportal.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.datetime.Instant

@Entity(tableName = "confidential")
data class Confidential(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "service_name") var serviceName: String,
    @ColumnInfo(name = "login_id") var loginId: String,
    var password: String? = null,
    var other: String? = null,
    @ColumnInfo(name = "created_at") val createdAt: Instant? = null,
    @ColumnInfo(name = "updated_at") val updatedAt: Instant? = null,
)
