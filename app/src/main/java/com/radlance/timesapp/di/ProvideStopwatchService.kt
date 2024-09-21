package com.radlance.timesapp.di

import com.radlance.presentation.StopwatchServiceInterface
import com.radlance.timesapp.StopwatchService
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface ProvideStopwatchService {
    @Binds
    fun provideService(stopwatchService: StopwatchService): StopwatchServiceInterface
}