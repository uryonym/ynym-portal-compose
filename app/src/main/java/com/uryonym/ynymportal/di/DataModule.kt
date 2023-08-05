package com.uryonym.ynymportal.di

import android.content.Context
import androidx.room.Room
import com.uryonym.ynymportal.data.TaskRepository
import com.uryonym.ynymportal.data.TaskRepositoryImpl
import com.uryonym.ynymportal.data.local.TaskDao
import com.uryonym.ynymportal.data.local.TaskDatabase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Singleton
    @Binds
    abstract fun bindTaskRepository(repository: TaskRepositoryImpl): TaskRepository
}

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): TaskDatabase {
        return Room.databaseBuilder(
            context = context.applicationContext,
            klass = TaskDatabase::class.java,
            name = "Tasks.db"
        ).build()
    }

    @Provides
    fun provideTaskDao(database: TaskDatabase): TaskDao = database.taskDao()
}
