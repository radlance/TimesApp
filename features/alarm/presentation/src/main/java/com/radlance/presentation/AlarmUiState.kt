package com.radlance.presentation

import com.radlance.domain.AlarmItem
import java.time.LocalDate
import java.util.Calendar

data class AlarmUiState(
    val alarmItems: List<AlarmItem> = List(3) {
        AlarmItem(
            id = it,
            time = Calendar.getInstance(),
            message = it.toString(),
            daysOfWeek = listOf(LocalDate.now().dayOfWeek),
            isEnabled = false
        )
    },
    val selectedItem: AlarmItem? = null
)