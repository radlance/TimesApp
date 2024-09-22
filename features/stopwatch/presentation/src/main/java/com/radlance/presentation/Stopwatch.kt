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

@Composable
fun Stopwatch(
    modifier: Modifier = Modifier,
    viewModel: StopwatchViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    val stopwatchUiState by viewModel.stopwatchState.collectAsState()

    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stopwatchUiState.formatElapsedTime(),
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
                        viewModel.commandService(context, ServiceState.START_OR_RESUME)
                    },
                    enabled = !stopwatchUiState.isEnabled,
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = stringResource(R.string.start))
                }
                Spacer(modifier = Modifier.width(32.dp))
                Button(
                    onClick = {
                        viewModel.commandService(context, ServiceState.PAUSE)
                    },
                    enabled = stopwatchUiState.isEnabled,
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = stringResource(R.string.pause))
                }

                Spacer(modifier = Modifier.width(32.dp))

                Button(onClick = {
                    viewModel.commandService(context, ServiceState.RESET)
                }, enabled = !stopwatchUiState.isEnabled && stopwatchUiState.elapsedTime != 0L, modifier = Modifier.weight(1f)) {
                    Text(text = stringResource(R.string.reset))
                }
            }
        }
    }
}