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
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.radlance.time.core.ServiceState

@Composable
fun CountdownTimer(
    modifier: Modifier = Modifier,
    viewModel: CountdownTimerViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    val countdownTimerUiState by viewModel.countdownTimerState.collectAsState()
    var hoursState by rememberSaveable { mutableIntStateOf(0) }
    var minutesState by rememberSaveable { mutableIntStateOf(0) }
    var secondsState by rememberSaveable { mutableIntStateOf(0) }

    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                SingleNumberSlider(
                    value = hoursState,
                    onValueChanged = { hoursState = it },
                    range = 0..23
                )

                Colon()

                SingleNumberSlider(
                    value = minutesState,
                    onValueChanged = { minutesState = it },
                    range = 0..59
                )

                Colon()

                SingleNumberSlider(
                    value = secondsState,
                    onValueChanged = { secondsState = it },
                    range = 0..59
                )
            }

            Column(

                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom
            ) {
                Row(modifier = Modifier.padding()) {
                    Box(
                        modifier = Modifier
                            .padding(bottom = 30.dp)
                            .clip(CircleShape)
                            .size(75.dp)
                            .background(MaterialTheme.colorScheme.primary)
                            .clickable(onClick = {
                                viewModel.commandService(
                                    context,
                                    ServiceState.RESET
                                )
                            })

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
        }


    }
}

@Composable
private fun SingleNumberSlider(
    value: Int,
    onValueChanged: (Int) -> Unit,
    range: IntRange
) {
    Box(
        modifier = Modifier.size(width = 180.dp, height = 140.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = value.toString().padStart(2, '0'),
                textAlign = TextAlign.Center,
                fontSize = 56.sp,
                style = MaterialTheme.typography.displayLarge,
            )
            Slider(
                value = value.toFloat(),
                onValueChange = { newValue ->
                    onValueChanged(newValue.toInt())
                },
                valueRange = range.first.toFloat()..range.last.toFloat(),
                modifier = Modifier.padding(horizontal = 8.dp)
            )
        }
    }
}

@Composable
fun Colon() {
    Text(text = "• •", fontSize = 48.sp, fontWeight = FontWeight.ExtraLight)
}