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
    val message: String,
    @ColumnInfo(name = "enabled") val isEnabled: Boolean,
    @PrimaryKey(autoGenerate = true) val id: Int = 0
)
