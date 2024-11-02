package com.radlance.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.radlance.presentation.components.HorizontalColon
import com.radlance.presentation.components.SingleNumberSlider
import com.radlance.presentation.components.VerticalColon
import com.radlance.uikit.ContentType

@Composable
internal fun TimeSetScreen(
    hours: Int,
    minutes: Int,
    seconds: Int,
    onHoursChanged: (Int) -> Unit,
    onMinutesChanged: (Int) -> Unit,
    onSecondsChanged: (Int) -> Unit,
    onStartTimer: () -> Unit,
    modifier: Modifier = Modifier,
    contentType: ContentType
) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        if (contentType == ContentType.Default) {
            PortraitScreen(
                hours,
                onHoursChanged,
                minutes,
                onMinutesChanged,
                seconds,
                onSecondsChanged,
                onStartTimer
            )
        } else {
            LandscapeScreen(
                hours,
                onHoursChanged,
                minutes,
                onMinutesChanged,
                seconds,
                onSecondsChanged,
                onStartTimer
            )
        }
    }
}

@Composable
private fun PortraitScreen(
    hours: Int,
    onHoursChanged: (Int) -> Unit,
    minutes: Int,
    onMinutesChanged: (Int) -> Unit,
    seconds: Int,
    onSecondsChanged: (Int) -> Unit,
    onStartTimer: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        SingleNumberSlider(
            value = hours,
            onValueChanged = onHoursChanged,
            range = 0..23
        )

        HorizontalColon()

        SingleNumberSlider(
            value = minutes,
            onValueChanged = onMinutesChanged,
            range = 0..59
        )

        HorizontalColon()

        SingleNumberSlider(
            value = seconds,
            onValueChanged = onSecondsChanged,
            range = 0..59
        )

        Box(
            modifier = Modifier
                .padding(top = 30.dp)
                .clip(CircleShape)
                .size(75.dp)
                .background(MaterialTheme.colorScheme.primary)
                .clickable {
                    if (hours != 0 || minutes != 0 || seconds != 0) {
                        onStartTimer()
                    }
                }
        ) {
            Icon(
                imageVector = Icons.Filled.PlayArrow,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier
                    .size(45.dp)
                    .align(Alignment.Center)
            )
        }
    }
}

@Composable
private fun LandscapeScreen(
    hours: Int,
    onHoursChanged: (Int) -> Unit,
    minutes: Int,
    onMinutesChanged: (Int) -> Unit,
    seconds: Int,
    onSecondsChanged: (Int) -> Unit,
    onStartTimer: () -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        SingleNumberSlider(
            value = hours,
            onValueChanged = onHoursChanged,
            range = 0..23
        )

        VerticalColon()

        SingleNumberSlider(
            value = minutes,
            onValueChanged = onMinutesChanged,
            range = 0..59
        )

        VerticalColon()

        SingleNumberSlider(
            value = seconds,
            onValueChanged = onSecondsChanged,
            range = 0..59
        )

        Box(
            modifier = Modifier
                .padding(start = 45.dp)
                .clip(CircleShape)
                .size(75.dp)
                .background(MaterialTheme.colorScheme.primary)
                .clickable {
                    if (hours != 0 || minutes != 0 || seconds != 0) {
                        onStartTimer()
                    }
                }
        ) {
            Icon(
                imageVector = Icons.Filled.PlayArrow,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier
                    .size(45.dp)
                    .align(Alignment.Center)
            )
        }
    }
}

