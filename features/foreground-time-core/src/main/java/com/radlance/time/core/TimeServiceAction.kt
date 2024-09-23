package com.radlance.time.core

import android.content.Context
import kotlinx.coroutines.flow.Flow

interface TimeServiceAction {
    fun getCurrentTime(): Flow<Long>
    fun getEnabledStatus(): Flow<Boolean>
    fun commandService(context: Context, serviceState: ServiceState)
}