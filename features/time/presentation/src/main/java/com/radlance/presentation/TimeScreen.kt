package com.radlance.presentation

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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.radlance.uikit.TimesAppTheme

@Composable
fun TimeScreen(
    modifier: Modifier = Modifier,
    viewModel: TimeViewModel = hiltViewModel()
) {
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
                            hour = hour.toString(),
                            minute = minute.toString(),
                            amOrPm = amOrPm,
                            country = country,
                            city = city
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun DigitalClockComponent(
    hour: String,
    minute: String,
    amOrPm: String,
    country: String,
    city: String
) {
    val formattedHour = if (hour.length == 1) "0$hour" else hour
    val formattedMinute = if (minute.length == 1) "0$minute" else minute

    Text(
        text = "$formattedHour:$formattedMinute $amOrPm",
        style = MaterialTheme.typography.titleLarge
    )
    Text(
        text = "$country, $city",
        style = MaterialTheme.typography.bodyMedium.merge(
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