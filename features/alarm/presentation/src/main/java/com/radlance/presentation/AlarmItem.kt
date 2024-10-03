package com.radlance.presentation

import android.icu.util.Calendar

data class AlarmItem(
    val id: Int = 0,
    val time: Calendar,
    val message: String,
    val isEnabled: Boolean
)
