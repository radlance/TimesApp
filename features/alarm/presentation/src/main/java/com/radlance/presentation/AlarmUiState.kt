package com.radlance.presentation

import com.radlance.domain.AlarmItem

data class AlarmUiState(
    val alarmItems: List<AlarmItem> = emptyList(),
    val selectedItem: AlarmItem? = null
)