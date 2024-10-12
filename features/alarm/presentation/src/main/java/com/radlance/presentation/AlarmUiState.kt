package com.radlance.presentation

import com.radlance.domain.AlarmItem

data class AlarmUiState(
    val alarmItems: List<AlarmItem>? = null,
    val selectedItem: AlarmItem? = null
)