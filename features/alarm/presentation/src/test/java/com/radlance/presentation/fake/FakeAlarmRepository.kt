package com.radlance.presentation.fake

import com.radlance.domain.AlarmItem
import com.radlance.domain.AlarmRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeAlarmRepository : AlarmRepository {
    private val alarmItems = FakeDataSource.alarmItems

    override fun getAlarmItems(): Flow<List<AlarmItem>> {
        return flow { emit(alarmItems.toList()) }
    }

    override suspend fun getLastAlarmItem(): AlarmItem {
        return alarmItems.last()
    }

    override suspend fun getAlarmItemById(id: Int): AlarmItem {
        return alarmItems.first { it.id == id }
    }

    override suspend fun updateAlarmItem(alarmItem: AlarmItem) {
        alarmItems.removeIf { it.id == alarmItem.id }
        alarmItems.add(alarmItem)
    }

    override suspend fun addAlarmItems(alarmItems: List<AlarmItem>) {
        this.alarmItems.addAll(alarmItems)
    }

    override suspend fun addAlarmItem(alarmItem: AlarmItem) {
        alarmItems.add(alarmItem)
    }

    override suspend fun deleteAlarmItem(alarmItem: AlarmItem) {
        alarmItems.remove(alarmItem)
    }
}