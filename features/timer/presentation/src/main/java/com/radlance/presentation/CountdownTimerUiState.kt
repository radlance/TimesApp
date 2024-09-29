package com.radlance.presentation

import java.util.Locale

data class CountdownTimerUiState(
    val remainingTime: Long = -1L,
    val initialTime: Long = -1L,
    val isEnabled: Boolean = false
) {
    fun getPercentProgress(): Float {
        return remainingTime.toFloat() / initialTime.toFloat()
    }

    fun formatRemainingTime(): String {
        if (remainingTime == 0L) return "00:00:00"

        val hours = remainingTime / (1000 * 60 * 60)
        val minutes = (remainingTime / (1000 * 60)) % 60
        val seconds = (remainingTime / 1000) % 60
        val milliseconds = ((remainingTime % 1000) / 10).toString().padStart(2, '0')

        return buildString {
            if (hours >= 1) {
                append(
                    String.format(
                        Locale.getDefault(),
                        "%02d:%02d:%02d",
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