package com.radlance.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import com.radlance.presentation.components.AlarmSetupComponent

@Composable
fun AlarmScreen(
    modifier: Modifier = Modifier,
    viewModel: AlarmViewModel = hiltViewModel()
) {
    val alarmState by viewModel.alarmState.collectAsState()

    var showSetupDialog by remember { mutableStateOf(false) }
    var isNewItem by remember { mutableStateOf(false) }


    Box(modifier = modifier.fillMaxSize()) {
        if (alarmState.alarmItems != null) {
            AnimatedVisibility(
                visible = alarmState.alarmItems!!.isEmpty(),
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                EmptyAlarmList()
            }

            AnimatedVisibility(
                visible = alarmState.alarmItems!!.isNotEmpty(),
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                AlarmList(
                    alarmItems = alarmState.alarmItems!!,
                    onItemClicked = {
                        viewModel.selectAlarmItem(it)
                        showSetupDialog = true
                    },
                    onCheckedChange = viewModel::switchAlarmState,
                    onDelete = viewModel::removeAlarmItem
                )
            }
        } else {
            AnimatedVisibility(
                visible = true,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                BaseScreen()
            }
        }

        if (showSetupDialog) {
            Dialog(
                onDismissRequest = { showSetupDialog = false },
                properties = DialogProperties(usePlatformDefaultWidth = false)
            ) {
                if (isNewItem) {
                    AlarmSetupComponent(
                        alarmItem = null,
                        onDaySelected = null,
                        onCancelClicked = {
                            showSetupDialog = false
                            isNewItem = false
                        },
                        onOkClicked = { viewModel.addAlarmItem(alarmItem = it) }
                    )
                } else {
                    AlarmSetupComponent(
                        alarmItem = alarmState.selectedItem,
                        onDaySelected = viewModel::changeDaysOfWeek,
                        onCancelClicked = { showSetupDialog = false },
                        onOkClicked = {
                            viewModel.updateAlarm(alarmItem = it)
                        }
                    )
                }
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
                    showSetupDialog = true
                    isNewItem = true
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
}

