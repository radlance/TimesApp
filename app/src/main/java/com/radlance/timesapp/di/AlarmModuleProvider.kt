package com.radlance.timesapp.di

import android.content.Context
import com.radlance.data.AlarmRepositoryImpl
import com.radlance.database.TimesDao
import com.radlance.database.TimesDatabase
import com.radlance.domain.AlarmRepository
import com.radlance.timesapp.services.AndroidAlarmScheduler
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AlarmModuleProvider {
    @Singleton
    @Provides
    fun provideAndroidAlarmScheduler(@ApplicationContext context: Context): AndroidAlarmScheduler {
        return AndroidAlarmScheduler(context)
    }

    @Singleton
    @Provides
    fun provideTimesDatabase(@ApplicationContext context: Context): TimesDatabase {
        return TimesDatabase(context)
    }

    @Singleton
    @Provides
    fun provideDao(timesDatabase: TimesDatabase): TimesDao {
        return timesDatabase.timesDao
    }

    @Singleton
    @Provides
    fun provideRepository(dao: TimesDao): AlarmRepository {
        return AlarmRepositoryImpl(dao)
    }
}