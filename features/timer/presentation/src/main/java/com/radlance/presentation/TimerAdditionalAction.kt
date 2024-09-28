package com.radlance.presentation

import com.radlance.time.core.TimeServiceAction
import kotlinx.coroutines.flow.Flow

interface TimerAdditionalAction : TimeServiceAction {
    fun setCountDownTime(time: Long)
    fun getInitialTime(): Flow<Long>
}