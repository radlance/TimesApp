package com.radlance.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.radlance.presentation.R

@Composable
internal fun SingleNumberSlider(
    value: Int,
    onValueChanged: (Int) -> Unit,
    range: IntRange
) {
    Box(
        modifier = Modifier.size(width = 180.dp, height = 140.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = value.toString().padStart(2, '0'),
                textAlign = TextAlign.Center,
                fontSize = 56.sp,
                style = MaterialTheme.typography.displayLarge,
            )
            Slider(
                value = value.toFloat(),
                onValueChange = { newValue ->
                    onValueChanged(newValue.toInt())
                },
                valueRange = range.first.toFloat()..range.last.toFloat(),
                modifier = Modifier.padding(horizontal = 8.dp)
            )
        }
    }
}

@Composable
internal fun HorizontalColon() {
    Text(
        text = stringResource(R.string.horizontal_colon),
        fontSize = 48.sp,
        fontWeight = FontWeight.ExtraLight
    )
}

@Composable
internal fun VerticalColon() {
    Text(
        text = stringResource(R.string.vertical_colon),
        fontSize = 48.sp,
        fontWeight = FontWeight.SemiBold,
        modifier = Modifier.padding(horizontal = 24.dp)
    )
}