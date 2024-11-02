package com.radlance.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.radlance.presentation.components.AnalogClockComponent
import com.radlance.presentation.components.DigitalClockComponent
import com.radlance.uikit.ContentType
import com.radlance.uikit.TimesAppTheme

@Composable
fun TimeScreen(
    contentType: ContentType,
    modifier: Modifier = Modifier,
    viewModel: TimeViewModel = viewModel()
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
                    if(contentType == ContentType.Default) {
                        PortraitScreen(hour, minute, seconds, timeZone)
                    } else {
                        LandscapeScreen(hour, minute, seconds, timeZone)
                    }
                }
            }
        }
    }
}

@Composable
private fun PortraitScreen(
    hour: Int,
    minute: Int,
    seconds: Int,
    timeZone: String
) {
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

@Composable
private fun LandscapeScreen(
    hour: Int,
    minute: Int,
    seconds: Int,
    timeZone: String
) {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        AnalogClockComponent(
            hour = hour,
            minute = minute,
            second = seconds,
            modifier = Modifier.fillMaxSize(fraction = 0.25f)
        )

        DigitalClockComponent(
            hour = hour,
            minute = minute,
            seconds = seconds,
            timeZone = timeZone
        )
    }
}


@Preview(showBackground = true)
@Composable
private fun TimeScreenPreview() {
    TimesAppTheme(darkTheme = true) {
        TimeScreen(ContentType.Default)
    }
}