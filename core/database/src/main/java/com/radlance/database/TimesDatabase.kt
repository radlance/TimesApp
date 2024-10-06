package com.radlance.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.radlance.database.entity.AlarmItemEntity


class TimesDatabase internal constructor(private val database: TimesRoomDatabase) {
    val timesDao: TimesDao
        get() = database.timesDao()
}

@Database(entities = [AlarmItemEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
internal abstract class TimesRoomDatabase : RoomDatabase() {
    abstract fun timesDao(): TimesDao
}

fun TimesDatabase(applicationContext: Context): TimesDatabase {
    val timesDatabase = Room.databaseBuilder(
        applicationContext,
        TimesRoomDatabase::class.java,
        "times_db"
    ).build()

    return TimesDatabase(timesDatabase)
}