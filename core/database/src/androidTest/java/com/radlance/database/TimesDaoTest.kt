package com.radlance.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.radlance.database.entity.AlarmItemEntity
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.time.DayOfWeek
import java.util.Calendar

@RunWith(AndroidJUnit4::class)
class TimesDaoTest {
    private lateinit var timesDatabase: TimesRoomDatabase
    private lateinit var timesDao: TimesDao

    private val alarmItem1 = AlarmItemEntity(
        time = Calendar.getInstance(),
        daysOfWeek = listOf(DayOfWeek.MONDAY, DayOfWeek.TUESDAY),
        message = "alarmItem1 message",
        isEnabled = true,
        currentCountOfTriggering = 1,
        id = 1
    )

    private val alarmItem2 = AlarmItemEntity(
        time = Calendar.getInstance(),
        daysOfWeek = listOf(DayOfWeek.SUNDAY, DayOfWeek.SATURDAY),
        message = "alarmItem2 message",
        isEnabled = false,
        currentCountOfTriggering = 2,
        id = 2
    )

    @Before
    fun setupDatabase() {
        val context: Context = ApplicationProvider.getApplicationContext()
        timesDatabase = Room.inMemoryDatabaseBuilder(context, TimesRoomDatabase::class.java)
            .allowMainThreadQueries()
            .build()

        timesDao = timesDatabase.timesDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        timesDatabase.close()
    }

    @Test
    @Throws(Exception::class)
    fun daoInsert_saveAlarmItems_insertAlarmItemsIntoDb() = runBlocking {
        assertEquals(emptyList<AlarmItemEntity>(), timesDao.getAlarmItems().first())

        timesDao.addAlarmItems(listOf(alarmItem1))
        timesDao.addAlarmItem(alarmItem2)

        assertEquals(listOf(alarmItem1, alarmItem2), timesDao.getAlarmItems().first())
    }

    @Test
    @Throws(Exception::class)
    fun daoQuery_getAlarmItemById_returnsAlarmEntityWithUniqueId() = runBlocking {
        timesDao.addAlarmItems(listOf(alarmItem1, alarmItem2))

        val foundedAlarmItem = timesDao.getAlarmItemById(alarmItem1.id)

        assertEquals(alarmItem1.id, foundedAlarmItem.id)
    }

    @Test
    @Throws(Exception::class)
    fun daoQuery_getLastAlarmItem_returnsLastInsertedAlarmItem() = runBlocking {
        assertEquals(null, timesDao.getLastAlarmItem())

        timesDao.addAlarmItem(alarmItem1)
        timesDao.addAlarmItem(alarmItem2)

        assertEquals(timesDao.getLastAlarmItem(), alarmItem2)
    }

    @Test
    @Throws(Exception::class)
    fun daoQuery_disableAlarmItemById_setFalseValueForEnabledField() = runBlocking {
        timesDao.addAlarmItem(alarmItem1)
        assertTrue(timesDao.getAlarmItemById(alarmItem1.id).isEnabled)

        timesDao.disableAlarmById(alarmItem1.id)
        assertFalse(timesDao.getAlarmItemById(alarmItem1.id).isEnabled)
    }

    @Test
    @Throws(Exception::class)
    fun daoUpdate_updateAlarmItem_changeAlarmEntityFieldIntoDb() = runBlocking {
        timesDao.addAlarmItems(listOf(alarmItem1, alarmItem2))

        val updatedItem1 = alarmItem1.copy(message = "updated alarmItem1")
        timesDao.updateAlarmItem(updatedItem1)

        assertEquals(listOf(updatedItem1, alarmItem2), timesDao.getAlarmItems().first())
    }

    @Test
    @Throws(Exception::class)
    fun daoDelete_deleteAlarmItem_removeAlarmItemFromDb() = runBlocking {
        timesDao.addAlarmItems(listOf(alarmItem1, alarmItem2))
        timesDao.deleteAlarmItem(alarmItem1)

        assertEquals(listOf(alarmItem2), timesDao.getAlarmItems().first())
    }
}