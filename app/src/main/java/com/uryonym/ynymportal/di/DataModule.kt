package com.uryonym.ynymportal.di

import android.content.Context
import androidx.room.Room
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.uryonym.ynymportal.data.AuthRepository
import com.uryonym.ynymportal.data.AuthRepositoryImpl
import com.uryonym.ynymportal.data.CarRepository
import com.uryonym.ynymportal.data.CarRepositoryImpl
import com.uryonym.ynymportal.data.ConfidentialRepository
import com.uryonym.ynymportal.data.ConfidentialRepositoryImpl
import com.uryonym.ynymportal.data.RefuelingRepository
import com.uryonym.ynymportal.data.RefuelingRepositoryImpl
import com.uryonym.ynymportal.data.TaskListRepository
import com.uryonym.ynymportal.data.TaskListRepositoryImpl
import com.uryonym.ynymportal.data.TaskRepository
import com.uryonym.ynymportal.data.TaskRepositoryImpl
import com.uryonym.ynymportal.data.local.CarDao
import com.uryonym.ynymportal.data.local.ConfidentialDao
import com.uryonym.ynymportal.data.local.ConfidentialLocalDataSource
import com.uryonym.ynymportal.data.local.ConfidentialLocalDataSourceImpl
import com.uryonym.ynymportal.data.local.RefuelingDao
import com.uryonym.ynymportal.data.local.TaskDao
import com.uryonym.ynymportal.data.local.TaskListDao
import com.uryonym.ynymportal.data.local.TaskListLocalDataSource
import com.uryonym.ynymportal.data.local.TaskListLocalDataSourceImpl
import com.uryonym.ynymportal.data.local.TaskLocalDataSource
import com.uryonym.ynymportal.data.local.TaskLocalDataSourceImpl
import com.uryonym.ynymportal.data.local.YnymPortalDatabase
import com.uryonym.ynymportal.data.remote.ConfidentialApiService
import com.uryonym.ynymportal.data.remote.ConfidentialRemoteDataSource
import com.uryonym.ynymportal.data.remote.ConfidentialRemoteDataSourceImpl
import com.uryonym.ynymportal.data.remote.TaskApiService
import com.uryonym.ynymportal.data.remote.TaskListApiService
import com.uryonym.ynymportal.data.remote.TaskListRemoteDataSource
import com.uryonym.ynymportal.data.remote.TaskListRemoteDataSourceImpl
import com.uryonym.ynymportal.data.remote.TaskRemoteDataSource
import com.uryonym.ynymportal.data.remote.TaskRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Singleton
    @Binds
    abstract fun bindAuthRepository(repository: AuthRepositoryImpl): AuthRepository

    @Singleton
    @Binds
    abstract fun bindTaskListRepository(repository: TaskListRepositoryImpl): TaskListRepository

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
abstract class DataSourceModule {
    @Singleton
    @Binds
    abstract fun bindTaskListLocalDataSource(dataSource: TaskListLocalDataSourceImpl): TaskListLocalDataSource

    @Singleton
    @Binds
    abstract fun bindTaskListRemoteDataSource(dataSource: TaskListRemoteDataSourceImpl): TaskListRemoteDataSource

    @Singleton
    @Binds
    abstract fun bindTaskLocalDataSource(dataSource: TaskLocalDataSourceImpl): TaskLocalDataSource

    @Singleton
    @Binds
    abstract fun bindTaskRemoteDataSource(dataSource: TaskRemoteDataSourceImpl): TaskRemoteDataSource

    @Singleton
    @Binds
    abstract fun bindConfidentialLocalDataSource(dataSource: ConfidentialLocalDataSourceImpl): ConfidentialLocalDataSource

    @Singleton
    @Binds
    abstract fun bindConfidentialRemoteDataSource(dataSource: ConfidentialRemoteDataSourceImpl): ConfidentialRemoteDataSource
}

//private const val BASE_URL = "http:/10.0.2.2:3000/api/v1/"
private const val BASE_URL = "https://api-portal.uryonym.com/api/v1/"

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Singleton
    @Provides
    fun provideNetwork(): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(Json.asConverterFactory(MediaType.get("application/json")))
            .baseUrl(BASE_URL)
            .build()
    }

    @Provides
    @Singleton
    fun provideTaskListService(retrofit: Retrofit): TaskListApiService =
        retrofit.create(TaskListApiService::class.java)

    @Provides
    @Singleton
    fun provideTaskService(retrofit: Retrofit): TaskApiService =
        retrofit.create(TaskApiService::class.java)

    @Provides
    @Singleton
    fun provideConfidentialService(retrofit: Retrofit): ConfidentialApiService =
        retrofit.create(ConfidentialApiService::class.java)
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
