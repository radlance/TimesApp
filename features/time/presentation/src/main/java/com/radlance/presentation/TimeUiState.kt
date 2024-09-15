package com.radlance.presentation

data class TimeUiState(
    val country: String = "United States",
    val city: String = "Mountain View",
    val hour: Int = 0,
    val minute: Int = 0,
    val seconds: Int = 0,
    val amOrPm: String = "",
)
