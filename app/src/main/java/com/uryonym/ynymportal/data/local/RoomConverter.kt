package com.uryonym.ynymportal.data.local

import androidx.room.TypeConverter
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate

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
