package com.radlance.presentation

import android.content.Context
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.radlance.time.core.ServiceState
import com.radlance.uikit.ContentType

@Composable
fun StopwatchScreen(
    contentType: ContentType,
    modifier: Modifier = Modifier,
    viewModel: StopwatchViewModel = hiltViewModel(),
) {
    val context = LocalContext.current

    val stopwatchUiState by viewModel.stopwatchState.collectAsState()


    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        if (contentType == ContentType.Default) {
            PortraitScreen(stopwatchUiState, viewModel, context, Modifier.align(Alignment.BottomCenter))
        } else {
            LandscapeScreen(stopwatchUiState, viewModel, context)
        }
    }
}

@Composable
private fun PortraitScreen(
    stopwatchUiState: StopwatchUiState,
    viewModel: StopwatchViewModel,
    context: Context,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            val icon = if (stopwatchUiState.isEnabled) {
                Icons.Filled.Pause
            } else {
                Icons.Filled.PlayArrow
            }

            val onClick = if (stopwatchUiState.isEnabled) {
                { viewModel.commandService(context, ServiceState.PAUSE) }
            } else {
                { viewModel.commandService(context, ServiceState.START_OR_RESUME) }
            }

            CustomButton(icon = icon, onclick = onClick)
            CustomButton(
                icon = Icons.Filled.Stop,
                onclick = {
                    viewModel.commandService(context, ServiceState.RESET)
                }
            )
        }
    }
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
}

@Composable
private fun LandscapeScreen(
    stopwatchUiState: StopwatchUiState,
    viewModel: StopwatchViewModel,
    context: Context
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = stopwatchUiState.formatElapsedTime(),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.displayLarge,
            fontSize = 100.sp,
            modifier = Modifier
                .animateContentSize()
                .fillMaxWidth()
        )
    }
    Column(
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.End,
        modifier = Modifier
            .fillMaxSize()
            .padding(end = 81.dp)
    ) {
        val icon = if (stopwatchUiState.isEnabled) {
            Icons.Filled.Pause
        } else {
            Icons.Filled.PlayArrow
        }

        val onClick = if (stopwatchUiState.isEnabled) {
            { viewModel.commandService(context, ServiceState.PAUSE) }
        } else {
            { viewModel.commandService(context, ServiceState.START_OR_RESUME) }
        }

        CustomButton(icon = icon, onclick = onClick)
        CustomButton(
            icon = Icons.Filled.Stop,
            onclick = {
                viewModel.commandService(context, ServiceState.RESET)
            },
            paddingValues = PaddingValues()
        )
    }
}

@Composable
private fun CustomButton(
    icon: ImageVector,
    onclick: () -> Unit,
    paddingValues: PaddingValues = PaddingValues(bottom = 30.dp)
) {
    Box(
        modifier = Modifier
            .padding(paddingValues)
            .clip(CircleShape)
            .size(75.dp)
            .background(MaterialTheme.colorScheme.primary)
            .clickable { onclick() }

    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier
                .size(45.dp)
                .align(Alignment.Center)
        )
    }
}