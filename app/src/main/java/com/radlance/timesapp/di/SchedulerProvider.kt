package com.radlance.timesapp.di

import android.content.Context
import com.radlance.timesapp.services.AndroidAlarmScheduler
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SchedulerProvider {
    @Singleton
    @Provides
    fun provideAndroidAlarmScheduler(@ApplicationContext context: Context): AndroidAlarmScheduler {
        return AndroidAlarmScheduler(context)
    }
}