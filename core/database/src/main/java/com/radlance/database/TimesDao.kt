package com.radlance.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.radlance.database.entity.AlarmItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TimesDao {
    @Query("SELECT * FROM alarm_item")
    fun getAlarmItems(): Flow<List<AlarmItemEntity>>

    @Query("UPDATE alarm_item SET enabled = 0 WHERE id = :id")
    suspend fun disableAlarmById(id: Int)

    @Update
    suspend fun updateAlarmItem(alarmItemEntity: AlarmItemEntity)

    @Insert
    suspend fun addAlarmItems(alarmItemEntities: List<AlarmItemEntity>)

    @Insert
    suspend fun addAlarmItem(alarmItemEntity: AlarmItemEntity)

    @Delete
    suspend fun deleteAlarmItem(alarmItemEntity: AlarmItemEntity)
}