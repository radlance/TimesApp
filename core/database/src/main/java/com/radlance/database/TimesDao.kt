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

    @Update
    suspend fun updateAlarmItem(alarmItemEntity: AlarmItemEntity)

    @Insert
    suspend fun addAlarmItem(alarmItemEntity: AlarmItemEntity)

    @Delete
    suspend fun deleteAlarmItem(alarmItemEntity: AlarmItemEntity)
}