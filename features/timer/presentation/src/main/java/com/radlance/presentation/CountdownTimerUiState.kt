package com.radlance.presentation

data class CountdownTimerUiState(
    val remainingTime: Long = 0L,
    val isEnabled: Boolean = false
)