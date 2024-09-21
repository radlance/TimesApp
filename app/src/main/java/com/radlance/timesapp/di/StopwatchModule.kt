package com.radlance.timesapp.di

import android.content.Context
import com.radlance.presentation.StopwatchServiceInterface
import com.radlance.timesapp.StopwatchService
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class StopwatchModule {

    @Provides
    @Singleton
    fun provideContext(@ApplicationContext context: Context) = context
}