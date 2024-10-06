package com.radlance.timesapp.di

import com.radlance.presentation.AlarmScheduler
import com.radlance.timesapp.services.AndroidAlarmScheduler
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface AlarmModule {
    @Binds
    fun provideScheduler(alarmScheduler: AndroidAlarmScheduler): AlarmScheduler
}
