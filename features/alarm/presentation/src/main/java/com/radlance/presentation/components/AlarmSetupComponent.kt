package com.radlance.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.radlance.domain.AlarmItem
import com.radlance.presentation.R
import java.time.DayOfWeek
import java.time.LocalDate
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun AlarmSetupComponent(
    alarmItem: AlarmItem?,
    onDaySelected: ((DayOfWeek) -> Unit)?,
    onCancelClicked: () -> Unit,
    onOkClicked: (AlarmItem) -> Unit,
    modifier: Modifier = Modifier
) {
    val selectedDaysOfWeek = remember { mutableStateListOf(LocalDate.now().dayOfWeek) }

    val timePickerState = rememberTimePickerState(
        initialHour = alarmItem?.time?.get(Calendar.HOUR_OF_DAY) ?: 12,
        initialMinute = alarmItem?.time?.get(Calendar.MINUTE) ?: 0
    )

    Card(
        modifier = modifier.padding(start = 16.dp, end = 16.dp),
        shape = RoundedCornerShape(24.dp)
    ) {
        LazyColumn {
            item {
                Box(
                    modifier = Modifier.padding(
                        top = 32.dp,
                        bottom = 8.dp,
                        start = 16.dp,
                        end = 16.dp
                    ),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        TimePicker(timePickerState)
                        WeekDaySelector(
                            selectedDays = alarmItem?.daysOfWeek ?: selectedDaysOfWeek,
                            onDaySelected = {
                                if (onDaySelected != null) {
                                    onDaySelected(it)
                                } else if (selectedDaysOfWeek.contains(it)) {
                                    selectedDaysOfWeek.remove(it)
                                } else {
                                    selectedDaysOfWeek.add(it)
                                }
                            }
                        )
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    TextButton(onClick = onCancelClicked) {
                        Text(text = stringResource(R.string.cancel), fontSize = 18.sp)
                    }
                    TextButton(
                        enabled = alarmItem?.daysOfWeek?.isNotEmpty()
                            ?: selectedDaysOfWeek.isNotEmpty(),
                        onClick = {
                            val pickedTime = Calendar.getInstance().apply {
                                set(Calendar.YEAR, get(Calendar.YEAR))
                                set(Calendar.MONTH, get(Calendar.MONTH))
                                set(Calendar.DAY_OF_MONTH, get(Calendar.DAY_OF_MONTH))
                                set(Calendar.HOUR_OF_DAY, timePickerState.hour)
                                set(Calendar.MINUTE, timePickerState.minute)
                                set(Calendar.SECOND, 0)
                                set(Calendar.MILLISECOND, 0)
                            }
                            val newAlarmItem = AlarmItem(
                                time = pickedTime,
                                daysOfWeek = selectedDaysOfWeek,
                                isEnabled = true
                            )

                            onOkClicked(alarmItem?.copy(time = pickedTime) ?: newAlarmItem)
                            onCancelClicked()
                        }
                    ) {
                        Text(text = stringResource(R.string.save), fontSize = 18.sp)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}


@Preview
@Composable
private fun AlarmSetupComponentPreview(modifier: Modifier = Modifier) {
    AlarmSetupComponent(
        AlarmItem(
            id = 1,
            time = Calendar.getInstance(),
            daysOfWeek = listOf(DayOfWeek.MONDAY),
            isEnabled = false
        ),
        onDaySelected = {},
        onCancelClicked = {},
        onOkClicked = {}
    )
}