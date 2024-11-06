package com.radlance.data

import com.radlance.database.entity.AlarmItemEntity
import com.radlance.domain.AlarmItem
import junit.framework.TestCase.assertEquals
import org.junit.Test
import java.time.DayOfWeek
import java.util.Calendar

class MappersTest {

    @Test
    fun alarmItemEntityExtension_toAlarmItem_returnsAlarmItemWithTheSameFields() {
        val alamItemEntity = AlarmItemEntity(
            time = Calendar.getInstance(),
            daysOfWeek = listOf(DayOfWeek.SUNDAY, DayOfWeek.SATURDAY),
            isEnabled = true,
            currentCountOfTriggering = 1,
            id = 1
        )

        val alarmItem = alamItemEntity.toAlarmItem()

        assertEquals(alamItemEntity.time, alarmItem.time)
        assertEquals(alamItemEntity.daysOfWeek, alarmItem.daysOfWeek)
        assertEquals(alamItemEntity.isEnabled, alarmItem.isEnabled)
        assertEquals(alamItemEntity.id, alarmItem.id)
    }

    @Test
    fun alarmItemExtension_toAlarmItemEntity_returnsAlarmItemEntityWithTheSameFields() {
        val alamItem = AlarmItem(
            time = Calendar.getInstance(),
            daysOfWeek = listOf(DayOfWeek.MONDAY, DayOfWeek.TUESDAY),
            isEnabled = false,
            id = 2
        )

        val alarmItemEntity = alamItem.toAlarmItemEntity()

        assertEquals(alamItem.time, alarmItemEntity.time)
        assertEquals(alamItem.daysOfWeek, alamItem.daysOfWeek)
        assertEquals(alamItem.isEnabled, alarmItemEntity.isEnabled)
        assertEquals(alamItem.id, alarmItemEntity.id)
    }
}