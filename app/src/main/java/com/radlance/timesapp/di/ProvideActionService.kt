package com.radlance.timesapp.di

import com.radlance.presentation.TimerAdditionalAction
import com.radlance.time.core.TimeServiceAction
import com.radlance.timesapp.services.CountdownTimerService
import com.radlance.timesapp.services.StopwatchService
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface ProvideActionService {
    @Binds
    fun provideStopwatchService(stopwatchService: StopwatchService): TimeServiceAction

    @Binds
    fun provideCountdownService(countdownTimerService: CountdownTimerService): TimerAdditionalAction
}