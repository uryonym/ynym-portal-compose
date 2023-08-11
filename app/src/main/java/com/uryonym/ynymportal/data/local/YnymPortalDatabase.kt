package com.uryonym.ynymportal.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.uryonym.ynymportal.data.model.Car
import com.uryonym.ynymportal.data.model.Confidential
import com.uryonym.ynymportal.data.model.Task

@Database(
    entities = [Task::class, Confidential::class, Car::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(DateConverter::class, DateTimeConverter::class)
abstract class YnymPortalDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao

    abstract fun confidentialDao(): ConfidentialDao

    abstract fun carDao(): CarDao
}
