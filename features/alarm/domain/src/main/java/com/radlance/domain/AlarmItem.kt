package com.radlance.domain

import java.time.DayOfWeek
import java.util.Calendar

data class AlarmItem(
    val id: Int = 0,
    val time: Calendar,
    val daysOfWeek: List<DayOfWeek>,
    val message: String,
    val isEnabled: Boolean
)
