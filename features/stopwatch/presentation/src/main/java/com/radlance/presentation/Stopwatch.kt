package com.radlance.presentation

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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun Stopwatch(
    modifier: Modifier = Modifier,
    viewModel: StopWatchViewModel = viewModel()
) {
    val elapsedTime by viewModel.elapsedTimeState.collectAsState()
    var isPaused by rememberSaveable { mutableStateOf(false) }
    var isStarted by rememberSaveable { mutableStateOf(false) }

    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = elapsedTime.formattedTime,
                style = MaterialTheme.typography.displayLarge,
                fontSize = 72.sp
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
                        isPaused = !isPaused

                        if (!isPaused) {
                            viewModel.pauseStopwatch()
                        } else {
                            isStarted = true
                            viewModel.startStopwatch()
                        }

                    },
                    modifier = Modifier.weight(1f)
                ) {
                    val text = if (isPaused) {
                        stringResource(R.string.pause)
                    } else {
                        stringResource(R.string.start)
                    }
                    Text(text = text)
                }

                Spacer(modifier = Modifier.width(32.dp))

                Button(onClick = {
                    viewModel.resetStopwatch()
                    isStarted = false
                    isPaused = false
                }, enabled = isStarted, modifier = Modifier.weight(1f)) {
                    Text(text = stringResource(R.string.reset))
                }
            }
        }
    }
}