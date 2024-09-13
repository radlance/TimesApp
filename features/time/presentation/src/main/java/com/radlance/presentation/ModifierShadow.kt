package com.radlance.presentation

import android.annotation.SuppressLint
import android.graphics.BlurMaskFilter
import android.graphics.BlurMaskFilter.Blur.NORMAL
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@SuppressLint("SuspiciousModifierThen")
fun Modifier.shadowCircular(
    color: Color = Color.Black,
    offsetX: Dp = 0.dp,
    offsetY: Dp = 0.dp,
    blurRadius: Dp = 0.dp
) = then(
    drawBehind {
        drawIntoCanvas {canvas ->
            val paint = Paint()
            val frameworkPaint = paint.asFrameworkPaint()
            if (blurRadius != 0.dp) {
                frameworkPaint.maskFilter = (BlurMaskFilter(blurRadius.toPx(), NORMAL))
            }
            frameworkPaint.color = color.toArgb()

            canvas.drawCircle(
                center = Offset(x = center.x + offsetX.toPx(), y = center.y + offsetY.toPx()),
                radius = size.width / 2,
                paint = paint
            )
        }
    }
)