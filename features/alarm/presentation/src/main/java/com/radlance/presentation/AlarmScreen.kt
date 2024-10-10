package com.radlance.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.radlance.domain.AlarmItem
import com.radlance.presentation.components.AlarmItemComponent
import com.radlance.presentation.components.AlarmSetupComponent
import kotlinx.coroutines.delay

@Composable
fun AlarmScreen(
    modifier: Modifier = Modifier,
    viewModel: AlarmViewModel = hiltViewModel()
) {
    val alarmState by viewModel.alarmState.collectAsState()

    var showSetupDialog by remember { mutableStateOf(false) }
    var isNewItem by remember { mutableStateOf(false) }

    Box(modifier = modifier.fillMaxSize()) {
        LazyColumn {
            val sortedItems =
                alarmState.alarmItems.map {
                    it.copy(daysOfWeek = it.daysOfWeek.sortedBy { dayOfWeek -> dayOfWeek.value })
                }
            items(
                items = sortedItems,
                key = { alarmItem -> alarmItem.id }) { alarmItem ->

                SwipeToDeleteContainer(
                    item = alarmItem,
                    onDelete = { viewModel.removeAlarmItem(alarmItem) }
                ) { item ->
                    AlarmItemComponent(
                        alarmItem = item,
                        onItemClicked = {
                            viewModel.selectAlarmItem(item)
                            showSetupDialog = true
                        },
                        onCheckedChange = { viewModel.switchAlarmState(item, it) },
                        checked = item.isEnabled,
                        modifier = Modifier.padding(8.dp)
                    )
                }
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

@Composable
private fun SwipeToDeleteContainer(
    item: AlarmItem,
    onDelete: (AlarmItem) -> Unit,
    animationDuration: Int = 500,
    content: @Composable (AlarmItem) -> Unit
) {
    var isRemoved by remember { mutableStateOf(false) }
    val state = rememberSwipeToDismissBoxState(
        confirmValueChange = { value ->
            if (value == SwipeToDismissBoxValue.EndToStart) {
                isRemoved = true
                true
            } else {
                false
            }
        }
    )

    LaunchedEffect(key1 = isRemoved) {
        if (isRemoved) {
            delay(animationDuration.toLong())
            onDelete(item)
        }
    }

    AnimatedVisibility(
        visible = !isRemoved,
        exit = shrinkVertically(
            animationSpec = tween(durationMillis = animationDuration),
            shrinkTowards = Alignment.Top
        ) + fadeOut()
    ) {
        SwipeToDismissBox(
            state = state,
            backgroundContent = {},
            content = { content(item) },
            enableDismissFromStartToEnd = false
        )
    }
}