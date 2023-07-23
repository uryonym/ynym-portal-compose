package com.uryonym.ynymportal.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate

@Entity(tableName = "task")
data class LocalTask(
    @PrimaryKey val id: String,
    var title: String,
    var description: String? = null,
    @ColumnInfo(name = "dead_line") var deadLine: LocalDate? = null,
    @ColumnInfo(name = "is_complete") var isComplete: Boolean,
    @ColumnInfo(name = "created_at") val createdAt: Instant? = null,
    @ColumnInfo(name = "updated_at") val updatedAt: Instant? = null,
)

class DateConverter {
    @TypeConverter
    fun stringToLocalDate(value: String): LocalDate = LocalDate.parse(value)

    @TypeConverter
    fun localDateToString(value: LocalDate): String = value.toString()
}

class DateTimeConverter {
    @TypeConverter
    fun stringToInstant(value: String): Instant = Instant.parse(value)

    @TypeConverter
    fun instantToString(value: Instant): String = value.toString()
}
