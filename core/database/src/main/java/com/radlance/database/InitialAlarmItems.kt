package com.radlance.database

import com.radlance.database.entity.AlarmItemEntity
import java.time.DayOfWeek
import java.util.Calendar

val alarmItems = listOf(
    AlarmItemEntity(
        time = Calendar.getInstance().apply {
            set(Calendar.YEAR, get(Calendar.YEAR))
            set(Calendar.MONTH, get(Calendar.MONTH))
            set(Calendar.DAY_OF_MONTH, get(Calendar.DAY_OF_MONTH))
            set(Calendar.HOUR_OF_DAY, 8)
            set(Calendar.MINUTE, 30)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        },
        daysOfWeek = listOf(
            DayOfWeek.MONDAY,
            DayOfWeek.TUESDAY,
            DayOfWeek.WEDNESDAY,
            DayOfWeek.THURSDAY,
            DayOfWeek.FRIDAY
        ),
        message = "",
        isEnabled = false
    ),

    AlarmItemEntity(
        time = Calendar.getInstance().apply {
            set(Calendar.YEAR, get(Calendar.YEAR))
            set(Calendar.MONTH, get(Calendar.MONTH))
            set(Calendar.DAY_OF_MONTH, get(Calendar.DAY_OF_MONTH))
            set(Calendar.HOUR_OF_DAY, 9)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        },
        daysOfWeek = listOf(DayOfWeek.SUNDAY, DayOfWeek.SATURDAY),
        message = "",
        isEnabled = false
    )
)