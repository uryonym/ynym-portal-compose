package com.uryonym.ynymportal.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable
import java.util.UUID

data class TaskList(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val seq: Int
)

@Entity(tableName = "task_list")
data class LocalTaskList(
    @PrimaryKey val id: String,
    val name: String,
    val seq: Int
)

@Serializable
data class RemoteTaskList(
    val id: String,
    val name: String,
    val seq: Int
)

fun TaskList.toLocal() = LocalTaskList(
    id = this.id,
    name = this.name,
    seq = this.seq
)

fun TaskList.toRemote() = RemoteTaskList(
    id = this.id,
    name = this.name,
    seq = this.seq
)

fun LocalTaskList.toModel() = TaskList(
    id = this.id,
    name = this.name,
    seq = this.seq
)

fun RemoteTaskList.toModel() = TaskList(
    id = this.id,
    name = this.name,
    seq = this.seq
)

fun List<TaskList>.toLocal() = map {
    it.toLocal()
}

fun List<TaskList>.toRemote() = map {
    it.toRemote()
}

@JvmName("listLocalTaskListToModel")
fun List<LocalTaskList>.toModel() = map {
    it.toModel()
}

@JvmName("listRemoteTaskListToModel")
fun List<RemoteTaskList>.toModel() = map {
    it.toModel()
}
