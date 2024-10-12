package com.radlance.presentation

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.radlance.domain.AlarmItem
import com.radlance.presentation.components.AlarmItemComponent
import com.radlance.presentation.components.SwipeToDeleteContainer

@Composable
fun AlarmList(
    alarmItems: List<AlarmItem>,
    onItemClicked: (AlarmItem) -> Unit,
    onCheckedChange: (AlarmItem, Boolean) -> Unit,
    onDelete: (AlarmItem) -> Unit
) {
    LazyColumn {
        val sortedItems = alarmItems.map {
            it.copy(daysOfWeek = it.daysOfWeek.sortedBy { dayOfWeek -> dayOfWeek.value })
        }
        items(
            items = sortedItems,
            key = { alarmItem -> alarmItem.id }
        ) { alarmItem ->
            SwipeToDeleteContainer(
                item = alarmItem,
                isLastElement = alarmItems.size == 1,
                onDelete = onDelete
            ) { item ->
                AlarmItemComponent(
                    alarmItem = item,
                    onItemClicked = { onItemClicked(item) },
                    onCheckedChange = { onCheckedChange(item, it) },
                    checked = item.isEnabled,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
}