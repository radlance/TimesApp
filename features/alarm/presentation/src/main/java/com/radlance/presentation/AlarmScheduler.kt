package com.radlance.presentation

import com.radlance.domain.AlarmItem

interface AlarmScheduler {
    fun schedule(alarmItem: AlarmItem)
    fun cancel(alarmItem: AlarmItem)
}