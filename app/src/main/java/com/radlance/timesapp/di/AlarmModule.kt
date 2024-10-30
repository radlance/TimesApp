package com.radlance.timesapp.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface AlarmModule {
    @Binds
    fun provideScheduler(alarmScheduler: com.radlance.timesapp.services.AlarmScheduler): com.radlance.presentation.AlarmScheduler
}
