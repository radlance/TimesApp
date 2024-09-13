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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.radlance.uikit.TimesAppTheme

@Composable
fun TimeScreen(modifier: Modifier = Modifier) {
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
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    AnalogClockComponent(
                        hour = 1,
                        minute = 2,
                        second = 3
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    DigitalClockComponent(
                        hour = "1",
                        minute = "2",
                        amOrPm = "3",
                    )
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
) {
    Text(
        text = "$hour:$minute $amOrPm", style = MaterialTheme.typography.titleLarge
    )
    Text(
        text = "Berlin, Germany", style = MaterialTheme.typography.bodyMedium.merge(
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