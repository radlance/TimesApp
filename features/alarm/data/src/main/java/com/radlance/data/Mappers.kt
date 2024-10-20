package com.radlance.data

import com.radlance.database.entity.AlarmItemEntity
import com.radlance.domain.AlarmItem

fun AlarmItemEntity.toAlarmItem(): AlarmItem {
    return AlarmItem(
        time = time,
        daysOfWeek = daysOfWeek,
        message = message,
        isEnabled = isEnabled,
        id = id
    )
}

fun AlarmItem.toAlarmItemEntity(): AlarmItemEntity {
    return AlarmItemEntity(
        time = time,
        daysOfWeek = daysOfWeek,
        message = message,
        isEnabled = isEnabled,
        id = id
    )
}