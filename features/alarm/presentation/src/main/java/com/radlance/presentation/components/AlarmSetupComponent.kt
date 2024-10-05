package com.radlance.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.radlance.domain.AlarmItem
import java.time.DayOfWeek
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlarmSetupComponent(
    alarmItem: AlarmItem,
    onDaySelected: (DayOfWeek) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.padding(start = 16.dp, end = 16.dp),
        shape = RoundedCornerShape(24.dp)
    ) {
        Box(modifier = Modifier.padding(32.dp), contentAlignment = Alignment.Center) {
            val timePickerState = rememberTimePickerState(
                initialHour = alarmItem.time.get(Calendar.HOUR_OF_DAY),
                initialMinute = alarmItem.time.get(Calendar.MINUTE)
            )
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                TimePicker(timePickerState)
                WeekDaySelector(selectedDays = alarmItem.daysOfWeek, onDaySelected = onDaySelected)
            }
        }
    }

}

@Composable
fun WeekDaySelector(
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
            val textColor = if (isSelected) Color.White else Color.Black
            val backgroundColor =
                if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent
            val interactionSource = remember { MutableInteractionSource() }
            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .background(backgroundColor, shape = RoundedCornerShape(8.dp))
                    .clickable(indication = null, interactionSource = interactionSource) {
                        onDaySelected(day)
                    }
                    .padding(8.dp)
            ) {
                Text(text = day.toString().take(3), color = textColor, fontSize = 16.sp)
            }
        }
    }
}


@Preview
@Composable
fun AlarmSetupComponentPreview(modifier: Modifier = Modifier) {
    AlarmSetupComponent(
        AlarmItem(
            id = 1,
            time = Calendar.getInstance(),
            message = 1.toString(),
            daysOfWeek = listOf(DayOfWeek.MONDAY),
            isEnabled = false
        ),
        onDaySelected = {}
    )
}