package com.uryonym.ynymportal.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [LocalTask::class], version = 1, exportSchema = false)
@TypeConverters(DateConverter::class, DateTimeConverter::class)
abstract class TaskDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
}

