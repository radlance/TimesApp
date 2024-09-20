package com.radlance.presentation

import java.util.Locale

data class StopwatchUiState(
    val milliSeconds: Long = 0,
    val seconds: Long = 0,
    val minutes: Long = 0,
    val hours: Long = 0
) {
    val formattedTime = if (hours < 1) {
        String.format(
            Locale.getDefault(),
            "%02d:%02d.%02d",
            minutes,
            seconds,
            milliSeconds
        )
    } else {
        String.format(
            Locale.getDefault(),
            "%02d:%02d:%02d.%02d",
            hours,
            minutes,
            seconds,
            milliSeconds
        )
    }
}