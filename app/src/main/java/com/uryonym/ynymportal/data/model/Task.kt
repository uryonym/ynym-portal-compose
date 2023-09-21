package com.uryonym.ynymportal.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.UUID

data class Task(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val description: String,
    val deadLine: LocalDate?,
    val isComplete: Boolean,
    val taskListId: String,
    val createdAt: Instant
)

@Entity(tableName = "task")
data class LocalTask(
    @PrimaryKey val id: String,
    val title: String,
    val description: String,
    @ColumnInfo(name = "dead_line") val deadLine: LocalDate?,
    @ColumnInfo(name = "is_complete") val isComplete: Boolean,
    @ColumnInfo(name = "task_list_id") val taskListId: String,
    @ColumnInfo(name = "created_at") val createdAt: Instant
)

@Serializable
data class RemoteTask(
    val id: String,
    val title: String,
    val description: String,
    @SerialName(value = "dead_line") val deadLine: LocalDate?,
    @SerialName(value = "is_complete") val isComplete: Boolean,
    @SerialName(value = "task_list_id") val taskListId: String,
    @SerialName(value = "created_at") val createdAt: Instant
)

fun Task.toLocal() = LocalTask(
    id = this.id,
    title = this.title,
    description = this.description,
    deadLine = this.deadLine,
    isComplete = this.isComplete,
    taskListId = this.taskListId,
    createdAt = this.createdAt
)

fun Task.toRemote() = RemoteTask(
    id = this.id,
    title = this.title,
    description = this.description,
    deadLine = this.deadLine,
    isComplete = this.isComplete,
    taskListId = this.taskListId,
    createdAt = this.createdAt
)

fun LocalTask.toModel() = Task(
    id = this.id,
    title = this.title,
    description = this.description,
    deadLine = this.deadLine,
    isComplete = this.isComplete,
    taskListId = this.taskListId,
    createdAt = this.createdAt
)

fun RemoteTask.toModel() = Task(
    id = this.id,
    title = this.title,
    description = this.description,
    deadLine = this.deadLine,
    isComplete = this.isComplete,
    taskListId = this.taskListId,
    createdAt = this.createdAt
)

fun List<Task>.toLocal() = map {
    it.toLocal()
}

fun List<Task>.toRemote() = map {
    it.toRemote()
}

@JvmName("listLocalTaskToModel")
fun List<LocalTask>.toModel() = map {
    it.toModel()
}

@JvmName("listRemoteTaskToModel")
fun List<RemoteTask>.toModel() = map {
    it.toModel()
}
