package com.radlance.presentation.components

import android.text.format.DateFormat
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.radlance.domain.AlarmItem
import java.text.SimpleDateFormat
import java.time.DayOfWeek
import java.util.Calendar
import java.util.Locale

@Composable
fun AlarmItemComponent(
    alarmItem: AlarmItem,
    onItemItemClicked: () -> Unit,
    onCheckedChange: (Boolean) -> Unit,
    checked: Boolean,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .height(120.dp)
            .clickable { onItemItemClicked() },
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxHeight(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(Modifier.weight(1f)) {
                val is24HourFormat = DateFormat.is24HourFormat(LocalContext.current)

                val date = alarmItem.time.time
                val pattern = if(is24HourFormat) "HH:mm" else "hh:mm a"
                val sdf = SimpleDateFormat(pattern, Locale.getDefault())
                val formattedTime = sdf.format(date)

                Text(text = formattedTime, fontSize = 36.sp)
                Spacer(Modifier.height(16.dp))
                Text(
                    text = alarmItem.daysOfWeek.map { dayOfWeek ->
                        dayOfWeek.name.lowercase()
                            .replaceFirstChar {
                                if (it.isLowerCase()) it.titlecase() else it.toString()
                            }
                    }.joinToString { it.take(3) },
                    style = MaterialTheme.typography.bodyLarge.merge(
                        TextStyle(
                            color = MaterialTheme.colorScheme.onBackground.copy(
                                alpha = 0.6f
                            )
                        )
                    )
                )
            }

            Switch(checked = checked, onCheckedChange = onCheckedChange)
        }
    }
}

@Preview
@Composable
private fun AlarmItemComponentPreview() {
    AlarmItemComponent(
        AlarmItem(
            time = Calendar.getInstance(),
            message = "test message",
            daysOfWeek = listOf(DayOfWeek.MONDAY),
            isEnabled = true
        ),
        onItemItemClicked = {},
        onCheckedChange = {},
        checked = true
    )
}