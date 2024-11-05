package com.radlance.presentation.dummy

import com.radlance.domain.AlarmItem
import com.radlance.presentation.AlarmScheduler

class DummyAlarmScheduler : AlarmScheduler {
    override fun schedule(alarmItem: AlarmItem) {}

    override fun cancel(alarmItem: AlarmItem) {}
}