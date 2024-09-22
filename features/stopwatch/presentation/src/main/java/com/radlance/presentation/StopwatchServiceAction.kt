package com.radlance.presentation

import android.content.Context
import kotlinx.coroutines.flow.Flow

interface StopwatchServiceAction {
    fun getElapsedTime(): Flow<Long>
    fun getEnabledStatus(): Flow<Boolean>
    fun commandService(context: Context, serviceState: ServiceState)
}