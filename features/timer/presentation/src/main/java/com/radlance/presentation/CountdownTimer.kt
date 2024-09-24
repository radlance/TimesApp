package com.radlance.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
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
                Row(modifier = Modifier.padding(start = 32.dp, end = 32.dp, bottom = 32.dp)) {
                    Button(
                        onClick = {
                            viewModel.setCountDownTimer(30000L)
                            viewModel.commandService(context, ServiceState.START_OR_RESUME)
                        },
                        enabled = !countdownTimerUiState.isEnabled,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(text = stringResource(R.string.start))
                    }

                    Spacer(modifier = Modifier.width(32.dp))
                    Button(
                        onClick = {
                            viewModel.commandService(context, ServiceState.PAUSE)
                        },
                        enabled = countdownTimerUiState.isEnabled,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(text = stringResource(R.string.pause))
                    }

                    Spacer(modifier = Modifier.width(32.dp))

                    Button(
                        onClick = {
                            viewModel.commandService(context, ServiceState.RESET)
                        },
                        enabled = !countdownTimerUiState.isEnabled && countdownTimerUiState.remainingTime != 0L,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(text = stringResource(R.string.reset))
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