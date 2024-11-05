package com.radlance.presentation.fake

import com.radlance.domain.AlarmItem
import java.time.DayOfWeek
import java.util.Calendar

object FakeDataSource {
    private val alarmItem1 = AlarmItem(
        time = Calendar.getInstance(),
        daysOfWeek = listOf(DayOfWeek.MONDAY, DayOfWeek.TUESDAY),
        message = "alarmItem1 message",
        isEnabled = true,
        id = 1
    )

    private val alarmItem2 = AlarmItem(
        time = Calendar.getInstance(),
        daysOfWeek = listOf(DayOfWeek.FRIDAY, DayOfWeek.SATURDAY),
        message = "alarmItem2 message",
        isEnabled = false,
        id = 2
    )

    val alarmItems = mutableListOf(alarmItem1, alarmItem2)
}