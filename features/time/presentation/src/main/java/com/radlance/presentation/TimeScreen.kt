package com.radlance.presentation

import android.text.format.DateFormat
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.radlance.presentation.components.AnalogClockComponent
import com.radlance.uikit.TimesAppTheme

@Composable
fun TimeScreen(
    modifier: Modifier = Modifier,
    viewModel: TimeViewModel = hiltViewModel()
) {
    viewModel.startUpdatingTime()
    val timeUiState by viewModel.timeUiState.collectAsState()

    Box(
        modifier = modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .fillMaxHeight(fraction = 0.8f),
                contentAlignment = Alignment.Center
            ) {
                with(timeUiState) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        AnalogClockComponent(
                            hour = hour,
                            minute = minute,
                            second = seconds
                        )
                        Spacer(modifier = Modifier.height(24.dp))

                        DigitalClockComponent(
                            hour = hour,
                            minute = minute,
                            seconds = seconds,
                            timeZone = timeZone
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun DigitalClockComponent(
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
        )
    )
}


@Preview(showBackground = true)
@Composable
private fun TimeScreenPreview() {
    TimesAppTheme(darkTheme = true) {
        TimeScreen()
    }
}