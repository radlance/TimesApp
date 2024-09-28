package com.radlance.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.radlance.time.core.ServiceState

@Composable
fun CountdownTimer(
    viewModel: CountdownTimerViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    val countdownTimerUiState by viewModel.countdownTimerState.collectAsState()
    var hoursState by rememberSaveable { mutableIntStateOf(0) }
    var minutesState by rememberSaveable { mutableIntStateOf(0) }
    var secondsState by rememberSaveable { mutableIntStateOf(0) }

    if (!countdownTimerUiState.isEnabled) {
        TimeSetScreen(
            hours = hoursState,
            minutes = minutesState,
            seconds = secondsState,
            onHoursChanged = { hoursState = it },
            onMinutesChanged = { minutesState = it },
            onSecondsChanged = { secondsState = it },
            onStartTimer = {
                val totalMilliseconds =
                    (hoursState * 60 * 60 * 1000) + (minutesState * 60 * 1000) + (secondsState * 1000)

                viewModel.setCountDownTimer(totalMilliseconds.toLong())
                viewModel.commandService(context, ServiceState.START_OR_RESUME)
            }
        )
    } else {
        ProgressScreen(
            progress = countdownTimerUiState.getPercentProgress(),
            remainingTime = countdownTimerUiState.formatRemainingTime()
        )
    }
}


