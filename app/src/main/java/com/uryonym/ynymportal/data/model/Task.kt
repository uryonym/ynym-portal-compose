package com.uryonym.ynymportal.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate

@Entity(tableName = "task")
data class Task(
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
