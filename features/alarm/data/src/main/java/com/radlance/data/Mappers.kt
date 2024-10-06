package com.radlance.data

import com.radlance.database.entity.AlarmItemEntity
import com.radlance.domain.AlarmItem

fun AlarmItemEntity.toAlarmItem(): AlarmItem {
    return AlarmItem(time, daysOfWeek, message, isEnabled, id)
}

fun AlarmItem.toAlarmItemEntity(): AlarmItemEntity {
    return AlarmItemEntity(time, daysOfWeek, message, isEnabled, id)
}