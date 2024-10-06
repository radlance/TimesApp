package com.radlance.data

import com.radlance.database.TimesDao
import com.radlance.domain.AlarmItem
import com.radlance.domain.AlarmRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AlarmRepositoryImpl @Inject constructor(private val dao: TimesDao) : AlarmRepository {
    override fun getAlarmItems(): Flow<List<AlarmItem>> {
        return dao.getAlarmItems().map { entityList -> entityList.map { it.toAlarmItem() } }
    }

    override suspend fun updateAlarmItem(alarmItem: AlarmItem) {
        dao.updateAlarmItem(alarmItem.toAlarmItemEntity())
    }

    override suspend fun addAlarmItem(alarmItem: AlarmItem) {
        dao.addAlarmItem(alarmItem.toAlarmItemEntity())
    }

    override suspend fun deleteAlarmItem(alarmItem: AlarmItem) {
        dao.deleteAlarmItem(alarmItem.toAlarmItemEntity())
    }
}