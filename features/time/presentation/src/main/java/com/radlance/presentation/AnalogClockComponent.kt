package com.radlance.presentation

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.unit.dp
import com.radlance.uikit.AnalogClockHourHandColor
import com.radlance.uikit.AnalogClockHourHandColorDark
import com.radlance.uikit.AnalogClockInnerBoxColor
import com.radlance.uikit.AnalogClockInnerBoxColorDark
import com.radlance.uikit.AnalogClockInnerBoxShadow
import com.radlance.uikit.AnalogClockInnerBoxShadowDark
import com.radlance.uikit.AnalogClockMinuteHandColor
import com.radlance.uikit.AnalogClockMinuteHandColorDark
import com.radlance.uikit.AnalogClockOuterBoxColor
import com.radlance.uikit.AnalogClockOuterBoxColorDark
import com.radlance.uikit.AnalogClockSecondHandColor
import com.radlance.uikit.AnalogClockSecondHandColorDark
import kotlin.math.min

@Composable
fun AnalogClockComponent(
    hour: Int,
    minute: Int,
    second: Int,
) {

    val isDarkTheme = isSystemInDarkTheme()

    val outerBoxColor = if (isDarkTheme) AnalogClockOuterBoxColorDark else AnalogClockOuterBoxColor
    val innerBoxColor = if (isDarkTheme) AnalogClockInnerBoxColorDark else AnalogClockInnerBoxColor
    val innerBoxShadow =
        if (isDarkTheme) AnalogClockInnerBoxShadowDark else AnalogClockInnerBoxShadow
    val hourHandColor = if (isDarkTheme) AnalogClockHourHandColorDark else AnalogClockHourHandColor
    val minuteHandColor =
        if (isDarkTheme) AnalogClockMinuteHandColorDark else AnalogClockMinuteHandColor
    val secondHandColor =
        if (isDarkTheme) AnalogClockSecondHandColorDark else AnalogClockSecondHandColor

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize(fraction = 0.6f)
            .aspectRatio(1f)
            .clip(CircleShape)
            .background(outerBoxColor)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize(fraction = 0.78f)
                .aspectRatio(1f)
                .shadowCircular(
                    offsetX = 4.dp,
                    offsetY = 0.dp,
                    blurRadius = 10.dp,
                    color = innerBoxShadow
                )
                .clip(CircleShape)
                .background(innerBoxColor)
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val diameter = min(size.width, size.height) * 0.9f
                val radius = diameter / 2

                repeat(4) {
                    val start = center - Offset(0f, radius)
                    val end = start + Offset(0f, radius / 40f)
                    rotate(it / 4f * 360) {
                        drawLine(
                            color = Color.White,
                            start = start,
                            end = end,
                            strokeWidth = 5.dp.toPx(),
                            cap = StrokeCap.Round
                        )
                    }
                }

                val secondRatio = second / 60f
                val minuteRatio = minute / 60f
                val hourRatio = hour / 12f

                rotate(hourRatio * 360, center) {
                    drawLine(
                        color = hourHandColor,
                        start = center - Offset(0f, radius * 0.4f),
                        end = center + Offset(0f, radius * 0.1f),
                        strokeWidth = 3.8.dp.toPx(),
                        cap = StrokeCap.Round
                    )
                }

                rotate(minuteRatio * 360, center) {
                    drawLine(
                        color = minuteHandColor,
                        start = center - Offset(0f, radius * 0.6f),
                        end = center + Offset(0f, radius * 0.1f),
                        strokeWidth = 3.dp.toPx(),
                        cap = StrokeCap.Round
                    )
                }

                rotate(secondRatio * 360, center) {
                    drawLine(
                        color = secondHandColor,
                        start = center - Offset(0f, radius * 0.7f),
                        end = center + Offset(0f, radius * 0.1f),
                        strokeWidth = 3.dp.toPx(),
                        cap = StrokeCap.Round
                    )
                }

                drawCircle(
                    color = secondHandColor,
                    radius = 5.dp.toPx(),
                    center = center
                )
            }
        }
    }
}