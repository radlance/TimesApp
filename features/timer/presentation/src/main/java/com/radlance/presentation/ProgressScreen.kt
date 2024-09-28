package com.radlance.presentation

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ProgressScreen(
    modifier: Modifier = Modifier,
    progress: Float,
    remainingTime: String,
    isEnabled: Boolean,
    onResumeClick: () -> Unit,
    onPauseClick: () -> Unit,
    onStopClick: () -> Unit,
    progressColor: Color = MaterialTheme.colorScheme.secondary,
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
                    Canvas(
                        modifier = modifier
                            .aspectRatio(1f)
                            .fillMaxSize()
                            .padding(24.dp)
                    ) {
                        drawArc(
                            brush = brushBackground,
                            startAngle = 90f,
                            sweepAngle = PROGRESS_FULL_DEGREES,
                            useCenter = false,
                            style = drawStyle
                        )
                        drawArc(
                            brush = brush,
                            startAngle = 270f,
                            sweepAngle = progressDegrees,
                            useCenter = false,
                            style = drawStyle
                        )
                    }
                    Box(modifier = Modifier.align(Alignment.Center)) {
                        Text(text = remainingTime, style = MaterialTheme.typography.displayLarge, fontSize = 72.sp)
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
                    val icon = if(isEnabled) {
                        Icons.Filled.Pause
                    } else {
                        Icons.Filled.PlayArrow
                    }

                    val onClick = if(isEnabled) {
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
}

@Composable
private fun CustomButton(icon: ImageVector, onclick: () -> Unit) {
    Box(
        modifier = Modifier
            .padding(bottom = 30.dp)
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

private const val PROGRESS_FULL_DEGREES = 360f