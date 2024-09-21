package com.radlance.presentation

import android.content.Context
import androidx.lifecycle.LiveData

interface StopwatchServiceInterface {
    fun getElapsedTime(): LiveData<Long>
    fun getEnabledStatus(): LiveData<Boolean>
    fun commandService(context: Context, serviceState: SERVICESTATE)
}