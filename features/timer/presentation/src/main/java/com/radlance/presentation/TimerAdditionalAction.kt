package com.radlance.presentation

import com.radlance.time.core.TimeServiceAction

interface TimerAdditionalAction : TimeServiceAction {
    fun setCountDownTime(time: Long)
}