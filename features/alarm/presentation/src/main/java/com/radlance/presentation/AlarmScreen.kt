package com.radlance.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import com.radlance.presentation.components.AlarmItemComponent
import com.radlance.presentation.components.AlarmSetupComponent

@Composable
fun AlarmScreen(
    modifier: Modifier = Modifier,
    viewModel: AlarmViewModel = hiltViewModel()
) {

    val alarmState by viewModel.alarmState.collectAsState()

    var showSetupDialog by remember { mutableStateOf(false) }
    var selectedAlarmItem by remember { mutableStateOf<AlarmItem?>(null) }

    Box(modifier = modifier.fillMaxSize()) {
        LazyColumn(
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(items = alarmState, key = { alarmItem -> alarmItem.id }) { alarmItem ->
                AlarmItemComponent(
                    alarmItem = alarmItem,
                    onItemItemClicked = {
                        selectedAlarmItem = alarmItem
                        showSetupDialog = true
                    },
                    onCheckedChange = { viewModel.switchAlarmState(alarmItem, it) },
                    checked = alarmItem.isEnabled
                )
            }
        }

        if (showSetupDialog && selectedAlarmItem != null) {
            Dialog(
                onDismissRequest = { showSetupDialog = false },
                properties = DialogProperties(usePlatformDefaultWidth = false)
            ) {
                AlarmSetupComponent(alarmItem = selectedAlarmItem!!)
            }
        }

        Box(
            modifier = Modifier
                .padding(bottom = 30.dp)
                .align(Alignment.BottomCenter)
                .clip(CircleShape)
                .size(75.dp)
                .background(MaterialTheme.colorScheme.primary)
                .clickable {

                }
        ) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier
                    .size(45.dp)
                    .align(Alignment.Center)
            )
        }
    }
//    var alarmItem: AlarmItem? = null
//
//    var secondsText by remember {
//        mutableStateOf("")
//    }
//    var message by remember {
//        mutableStateOf("")
//    }
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp),
//        verticalArrangement = Arrangement.Center
//    ) {
//        val pickerState = rememberTimePickerState(
//            initialHour = LocalTime.now().hour,
//            initialMinute = LocalTime.now().minute
//        )
//
//        TimePicker(pickerState)
//
//        OutlinedTextField(
//            value = message,
//            onValueChange = { message = it },
//            modifier = Modifier.fillMaxWidth(),
//            placeholder = {
//                Text(text = "Message")
//            }
//        )
//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            horizontalArrangement = Arrangement.Center
//        ) {
//            Button(onClick = {
//                val pickedTime = Calendar.getInstance().apply {
//                    set(Calendar.YEAR, get(Calendar.YEAR))
//                    set(Calendar.MONTH, get(Calendar.MONTH))
//                    set(Calendar.DAY_OF_MONTH, get(Calendar.DAY_OF_MONTH))
//                    set(Calendar.HOUR_OF_DAY, pickerState.hour)
//                    set(Calendar.MINUTE, pickerState.minute)
//                    set(Calendar.SECOND, 0)
//                    set(Calendar.MILLISECOND, 0)
//                }
//
//                alarmItem = AlarmItem(
//                    time = pickedTime,
//                    message = message
//                )
//                alarmItem?.let(viewModel::schedule)
//                secondsText = ""
//                message = ""
//            }) {
//                Text(text = "Schedule")
//            }
//            Button(onClick = {
//                alarmItem?.let(viewModel::cancel)
//            }) {
//                Text(text = "Cancel")
//            }
//        }
//    }
}
