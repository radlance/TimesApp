package com.radlance.database

import androidx.room.TypeConverter
import java.time.DayOfWeek
import java.util.Calendar

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Calendar? {
        return value?.let { Calendar.getInstance().apply { timeInMillis = it } }
    }

    @TypeConverter
    fun dateToTimestamp(calendar: Calendar?): Long? {
        return calendar?.timeInMillis
    }

    @TypeConverter
    fun fromDayOfWeekList(daysOfWeek: List<DayOfWeek>): String {
        return daysOfWeek.joinToString(",") { it.value.toString() }
    }

    @TypeConverter
    fun toDayOfWeekList(value: String): List<DayOfWeek> {
        return value.split(",").map { DayOfWeek.of(it.toInt()) }
    }
}