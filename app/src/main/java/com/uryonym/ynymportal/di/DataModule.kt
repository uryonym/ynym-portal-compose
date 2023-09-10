package com.uryonym.ynymportal.di

import android.content.Context
import androidx.room.Room
import com.uryonym.ynymportal.data.AuthRepository
import com.uryonym.ynymportal.data.AuthRepositoryImpl
import com.uryonym.ynymportal.data.CarRepository
import com.uryonym.ynymportal.data.CarRepositoryImpl
import com.uryonym.ynymportal.data.ConfidentialRepository
import com.uryonym.ynymportal.data.ConfidentialRepositoryImpl
import com.uryonym.ynymportal.data.RefuelingRepository
import com.uryonym.ynymportal.data.RefuelingRepositoryImpl
import com.uryonym.ynymportal.data.TaskRepository
import com.uryonym.ynymportal.data.TaskRepositoryImpl
import com.uryonym.ynymportal.data.local.CarDao
import com.uryonym.ynymportal.data.local.ConfidentialDao
import com.uryonym.ynymportal.data.local.RefuelingDao
import com.uryonym.ynymportal.data.local.TaskDao
import com.uryonym.ynymportal.data.local.TaskListDao
import com.uryonym.ynymportal.data.local.YnymPortalDatabase
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
    abstract fun bindAuthRepository(repository: AuthRepositoryImpl): AuthRepository

    @Singleton
    @Binds
    abstract fun bindTaskRepository(repository: TaskRepositoryImpl): TaskRepository

    @Singleton
    @Binds
    abstract fun bindConfidentialRepository(repository: ConfidentialRepositoryImpl): ConfidentialRepository

    @Singleton
    @Binds
    abstract fun bindCarRepository(repository: CarRepositoryImpl): CarRepository

    @Singleton
    @Binds
    abstract fun bindRefuelingRepository(repository: RefuelingRepositoryImpl): RefuelingRepository
}

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): YnymPortalDatabase {
        return Room.databaseBuilder(
            context = context.applicationContext,
            klass = YnymPortalDatabase::class.java,
            name = "YnymPortal.db"
        ).build()
    }

    @Provides
    fun provideTaskListDao(database: YnymPortalDatabase): TaskListDao = database.taskListDao()

    @Provides
    fun provideTaskDao(database: YnymPortalDatabase): TaskDao = database.taskDao()

    @Provides
    fun provideConfidentialDao(database: YnymPortalDatabase): ConfidentialDao =
        database.confidentialDao()

    @Provides
    fun provideCarDao(database: YnymPortalDatabase): CarDao = database.carDao()

    @Provides
    fun provideRefuelingDao(database: YnymPortalDatabase): RefuelingDao = database.refuelingDao()
}
