package com.radlance.presentation

import android.icu.util.Calendar
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import java.sql.Time
import java.time.LocalTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlarmScreen(
    modifier: Modifier = Modifier,
    viewModel: AlarmViewModel = hiltViewModel()
) {
    var alarmItem: AlarmItem? = null

    var secondsText by remember {
        mutableStateOf("")
    }
    var message by remember {
        mutableStateOf("")
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        val pickerState = rememberTimePickerState(
            initialHour = LocalTime.now().hour,
            initialMinute = LocalTime.now().minute
        )

        TimePicker(pickerState)

        OutlinedTextField(
            value = message,
            onValueChange = { message = it },
            modifier = Modifier.fillMaxWidth(),
            placeholder = {
                Text(text = "Message")
            }
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(onClick = {
                val pickedTime = Calendar.getInstance().apply {
                    set(Calendar.YEAR, get(Calendar.YEAR))
                    set(Calendar.MONTH, get(Calendar.MONTH))
                    set(Calendar.DAY_OF_MONTH, get(Calendar.DAY_OF_MONTH))
                    set(Calendar.HOUR_OF_DAY, pickerState.hour)
                    set(Calendar.MINUTE, pickerState.minute)
                    set(Calendar.SECOND, 0)
                    set(Calendar.MILLISECOND, 0)
                }

                alarmItem = AlarmItem(
                    time = pickedTime,
                    message = message
                )
                alarmItem?.let(viewModel::schedule)
                secondsText = ""
                message = ""
            }) {
                Text(text = "Schedule")
            }
            Button(onClick = {
                alarmItem?.let(viewModel::cancel)
            }) {
                Text(text = "Cancel")
            }
        }
    }
}
