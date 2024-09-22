package com.radlance.timesapp.di

import com.radlance.presentation.StopwatchServiceAction
import com.radlance.timesapp.services.stopwatch.StopwatchService
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface ProvideStopwatchService {
    @Binds
    fun provideService(stopwatchService: StopwatchService): StopwatchServiceAction
}