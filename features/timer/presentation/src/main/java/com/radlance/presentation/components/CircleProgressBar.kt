package com.radlance.presentation.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.radlance.presentation.PROGRESS_FULL_DEGREES

@Composable
internal fun CircleProgressBar(
    modifier: Modifier,
    brushBackground: SolidColor,
    drawStyle: Stroke,
    brush: SolidColor,
    progressDegrees: Float
) {
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
}

