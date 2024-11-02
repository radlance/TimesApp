package com.radlance.presentation.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.DayOfWeek

@Composable
internal fun WeekDaySelector(
    selectedDays: List<DayOfWeek>,
    onDaySelected: (DayOfWeek) -> Unit
) {
    val daysOfWeek = listOf(
        DayOfWeek.MONDAY,
        DayOfWeek.TUESDAY,
        DayOfWeek.WEDNESDAY,
        DayOfWeek.THURSDAY,
        DayOfWeek.FRIDAY,
        DayOfWeek.SATURDAY,
        DayOfWeek.SUNDAY
    )

    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        items(items = daysOfWeek) { day ->
            val isSelected = selectedDays.contains(day)
            val textColor = if (isSelected) {
                MaterialTheme.colorScheme.surface
            } else {
                MaterialTheme.colorScheme.onSurface
            }
            val backgroundColor =
                if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent
            val animatedTextColor = animateColorAsState(targetValue = textColor, label = "")
            val animatedBackgroundColor =
                animateColorAsState(targetValue = backgroundColor, label = "")

            val interactionSource = remember { MutableInteractionSource() }
            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .background(animatedBackgroundColor.value, shape = RoundedCornerShape(8.dp))
                    .clickable(indication = null, interactionSource = interactionSource) {
                        onDaySelected(day)
                    }
                    .padding(8.dp)
            ) {
                Text(
                    text = day.toString().take(3),
                    color = animatedTextColor.value,
                    fontSize = 16.sp
                )
            }
        }
    }
}