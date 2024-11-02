package com.radlance.presentation

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.radlance.presentation.components.CircleProgressBar
import com.radlance.presentation.components.CustomButton
import com.radlance.uikit.ContentType

@Composable
internal fun ProgressScreen(
    contentType: ContentType,
    progress: Float,
    remainingTime: String,
    isEnabled: Boolean,
    onResumeClick: () -> Unit,
    onPauseClick: () -> Unit,
    onStopClick: () -> Unit,
    modifier: Modifier = Modifier,
    progressColor: Color = MaterialTheme.colorScheme.primary,
    backgroundColor: Color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)
) {

    val drawStyle = remember { Stroke(width = 24.dp.value, cap = StrokeCap.Round) }
    val brush = remember {
        SolidColor(progressColor)
    }

    val brushBackground = remember { SolidColor(backgroundColor) }
    val animateCurrentProgress = animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(durationMillis = 100, easing = LinearEasing), label = ""
    )

    val progressDegrees = animateCurrentProgress.value * PROGRESS_FULL_DEGREES


    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {

        if (contentType == ContentType.Default) {
            PortraitScreen(
                modifier,
                brushBackground,
                drawStyle,
                brush,
                progressDegrees,
                remainingTime,
                isEnabled,
                onPauseClick,
                onResumeClick,
                onStopClick
            )
        } else {
            LandscapeScreen(
                modifier,
                brushBackground,
                drawStyle,
                brush,
                progressDegrees,
                remainingTime,
                isEnabled,
                onPauseClick,
                onResumeClick,
                onStopClick
            )
        }
    }
}

@Composable
private fun PortraitScreen(
    modifier: Modifier,
    brushBackground: SolidColor,
    drawStyle: Stroke,
    brush: SolidColor,
    progressDegrees: Float,
    remainingTime: String,
    isEnabled: Boolean,
    onPauseClick: () -> Unit,
    onResumeClick: () -> Unit,
    onStopClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box {
                CircleProgressBar(modifier, brushBackground, drawStyle, brush, progressDegrees)
                Box(modifier = Modifier.align(Alignment.Center)) {
                    Text(
                        text = remainingTime,
                        style = MaterialTheme.typography.displayLarge,
                        fontSize = 72.sp,
                        modifier = Modifier.animateContentSize()
                    )
                }
            }
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                val icon = if (isEnabled) {
                    Icons.Filled.Pause
                } else {
                    Icons.Filled.PlayArrow
                }

                val onClick = if (isEnabled) {
                    { onPauseClick() }
                } else {
                    { onResumeClick() }
                }

                CustomButton(icon = icon, onclick = onClick)
                CustomButton(icon = Icons.Filled.Stop, onclick = onStopClick)
            }
        }
    }
}

@Composable
private fun LandscapeScreen(
    modifier: Modifier,
    brushBackground: SolidColor,
    drawStyle: Stroke,
    brush: SolidColor,
    progressDegrees: Float,
    remainingTime: String,
    isEnabled: Boolean,
    onPauseClick: () -> Unit,
    onResumeClick: () -> Unit,
    onStopClick: () -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Box {
            CircleProgressBar(modifier, brushBackground, drawStyle, brush, progressDegrees)
            Box(modifier = Modifier.align(Alignment.Center)) {
                Text(
                    text = remainingTime,
                    style = MaterialTheme.typography.displayLarge,
                    fontSize = 48.sp,
                    modifier = Modifier.animateContentSize()
                )
            }
        }
    }

    Column(
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .fillMaxSize()
            .padding(end = 81.dp)
    ) {

        val icon = if (isEnabled) {
            Icons.Filled.Pause
        } else {
            Icons.Filled.PlayArrow
        }

        val onClick = if (isEnabled) {
            { onPauseClick() }
        } else {
            { onResumeClick() }
        }

        CustomButton(icon = icon, onclick = onClick)
        CustomButton(
            icon = Icons.Filled.Stop,
            onclick = onStopClick,
            paddingValues = PaddingValues(0.dp)
        )
    }
}

internal const val PROGRESS_FULL_DEGREES = 360f

