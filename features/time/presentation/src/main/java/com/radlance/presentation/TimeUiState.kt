package com.radlance.presentation

data class TimeUiState(
    val hour: Int = 0,
    val minute: Int = 0,
    val seconds: Int = 0,
    val timeZone: String = "",
)
