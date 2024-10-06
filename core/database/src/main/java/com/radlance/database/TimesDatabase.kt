package com.radlance.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.radlance.database.entity.AlarmItemEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


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

    val callback = object : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            CoroutineScope(Dispatchers.IO).launch {
                val timesDao = TimesDatabase(applicationContext).timesDao
                timesDao.addAlarmItems(alarmItems)
            }
        }
    }

    val timesDatabase = Room.databaseBuilder(
        applicationContext,
        TimesRoomDatabase::class.java,
        "times_db"
    ).addCallback(callback).build()

    return TimesDatabase(timesDatabase)
}