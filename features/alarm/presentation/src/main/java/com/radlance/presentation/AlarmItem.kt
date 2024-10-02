package com.radlance.presentation

import android.icu.util.Calendar
import java.time.LocalDateTime

data class AlarmItem(
    val time: Calendar,
    val message: String
)
