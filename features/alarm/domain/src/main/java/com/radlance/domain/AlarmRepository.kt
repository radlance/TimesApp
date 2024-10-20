package com.radlance.domain

import kotlinx.coroutines.flow.Flow

interface AlarmRepository {
    fun getAlarmItems(): Flow<List<AlarmItem>>

    suspend fun getLastAlarmItem(): AlarmItem

    suspend fun updateAlarmItem(alarmItem: AlarmItem)

    suspend fun addAlarmItems(alarmItems: List<AlarmItem>)

    suspend fun addAlarmItem(alarmItem: AlarmItem)

    suspend fun deleteAlarmItem(alarmItem: AlarmItem)
}