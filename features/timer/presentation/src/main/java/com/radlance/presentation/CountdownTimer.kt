package com.radlance.presentation

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
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

    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = countdownTimerUiState.remainingTime.toString(),
                style = MaterialTheme.typography.displayLarge,
                fontSize = 72.sp,
                modifier = Modifier.animateContentSize()
            )
        }

        Column(
            modifier = Modifier.align(Alignment.BottomCenter),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {
            Row(modifier = Modifier.padding(32.dp)) {
                Button(
                    onClick = {
                        viewModel.setCountDownTimer(6000L)
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