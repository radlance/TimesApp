package com.radlance.presentation.components

import android.text.format.DateFormat
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp

@Composable
internal fun DigitalClockComponent(
    hour: Int,
    minute: Int,
    seconds: Int,
    timeZone: String
) {
    val is24HourFormat = DateFormat.is24HourFormat(LocalContext.current)

    val formattedHour = if (is24HourFormat) {
        if (hour < 10) "0$hour" else "$hour"
    } else {
        val hour12 = if (hour == 0 || hour == 12) 12 else hour % 12
        if (hour12 < 10) "0$hour12" else "$hour12"
    }

    val formattedMinute = if (minute < 10) "0$minute" else "$minute"
    val formattedSeconds = if (seconds < 10) "0$seconds" else "$seconds"
    val amPm = if (is24HourFormat) "" else if (hour < 12) "AM" else "PM"

    Column(verticalArrangement = Arrangement.Center) {
        Text(
            text = "$formattedHour:$formattedMinute:$formattedSeconds $amPm",
            style = MaterialTheme.typography.displayMedium
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = timeZone,
            style = MaterialTheme.typography.bodyLarge.merge(
                TextStyle(
                    color = MaterialTheme.colorScheme.onBackground.copy(
                        alpha = 0.6f
                    )
                )
            ),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}