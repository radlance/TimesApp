package com.radlance.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.DayOfWeek
import java.util.Calendar

@Entity(tableName = "alarm_item")
data class AlarmItemEntity(
    val time: Calendar,
    @ColumnInfo(name = "days_of_week") val daysOfWeek: List<DayOfWeek>,
    @ColumnInfo(name = "enabled") val isEnabled: Boolean,
    @ColumnInfo(name = "current_count_of_triggering") val currentCountOfTriggering: Int = 0,
    @PrimaryKey(autoGenerate = true) val id: Int = 0
)
