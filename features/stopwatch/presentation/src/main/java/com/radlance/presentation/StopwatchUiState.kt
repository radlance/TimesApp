package com.radlance.presentation

import java.util.Locale


data class StopwatchUiState(
    val elapsedTime: Long = 0,
    val isEnabled: Boolean = false
) {
    fun formatElapsedTime(): String {
        if (elapsedTime == 0L) return "00:00:00"

        val hours = elapsedTime / (1000 * 60 * 60)
        val minutes = (elapsedTime / (1000 * 60)) % 60
        val seconds = (elapsedTime / 1000) % 60
        val milliseconds = ((elapsedTime % 1000) / 10).toString().padStart(2, '0')

        return buildString {
            if (hours >= 1) {
                append(
                    String.format(
                        Locale.getDefault(),
                        "%02d:%02d:%02d:%s",
                        hours,
                        minutes,
                        seconds,
                        milliseconds
                    )
                )
            } else {
                append(
                    String.format(
                        Locale.getDefault(),
                        "%02d:%02d:%s",
                        minutes,
                        seconds,
                        milliseconds
                    )
                )
            }
        }
    }
}