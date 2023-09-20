package com.uryonym.ynymportal.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

data class Task(
    val id: String = "",
    var title: String,
    var description: String? = null,
    var deadLine: LocalDate? = null,
    var isComplete: Boolean,
    var uid: String,
    var taskListId: String,
    val createdAt: Instant? = null,
    val updatedAt: Instant? = null,
)

@Entity(tableName = "task")
data class LocalTask(
    @PrimaryKey val id: String = "",
    var title: String,
    var description: String? = null,
    @ColumnInfo(name = "dead_line") var deadLine: LocalDate? = null,
    @ColumnInfo(name = "is_complete") var isComplete: Boolean,
    var uid: String,
    @ColumnInfo(name = "task_list_id") var taskListId: String,
    @ColumnInfo(name = "created_at") val createdAt: Instant? = null,
    @ColumnInfo(name = "updated_at") val updatedAt: Instant? = null,
)

@Serializable
data class RemoteTask(
    val id: String = "",
    var title: String = "",
    var description: String? = null,
    @SerialName(value = "dead_line") var deadLine: LocalDate? = null,
    @SerialName(value = "is_complete") var isComplete: Boolean,
    val uid: String = "",
    @SerialName(value = "task_list_id") val taskListId: String = "",
    @SerialName(value = "created_at") val createdAt: Instant? = null,
    @SerialName(value = "updated_at") val updatedAt: Instant? = null,
)
