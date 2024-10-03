package com.radlance.presentation

import java.util.Calendar

data class AlarmItem(
    val id: Int = 0,
    val time: Calendar,
    val message: String,
    val isEnabled: Boolean
)
