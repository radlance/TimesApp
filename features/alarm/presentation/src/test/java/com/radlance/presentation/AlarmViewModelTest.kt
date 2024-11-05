package com.radlance.presentation

import com.radlance.domain.AlarmItem
import com.radlance.presentation.dummy.DummyAlarmScheduler
import com.radlance.presentation.fake.FakeAlarmRepository
import com.radlance.test.extension.TestDispatcherRule
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.time.DayOfWeek
import java.util.Calendar

class AlarmViewModelTest {
    @get:Rule
    val testDispatcher = TestDispatcherRule()

    private lateinit var viewModel: AlarmViewModel
    private lateinit var repository: FakeAlarmRepository


    @Before
    fun setup() {
        val alarmScheduler = DummyAlarmScheduler()
        repository = FakeAlarmRepository()
        viewModel = AlarmViewModel(alarmScheduler, repository)
    }

    @Test
    fun viewModel_switchAlarmState_changesAlarmState() = runTest {
        val alarmItemId = 1

        toggleAlarmState(alarmItemId, true)
        toggleAlarmState(alarmItemId, false)
    }

    private suspend fun toggleAlarmState(alarmItemId: Int, newState: Boolean) {
        val alarmItem = repository.getAlarmItemById(alarmItemId)
        viewModel.switchAlarmState(alarmItem, newState)

        val updatedAlarmItem = repository.getAlarmItemById(alarmItemId)
        assertEquals(newState, updatedAlarmItem.isEnabled)
    }

    @Test
    fun viewModel_addAlarmItem_addsNewAlarm() = runTest {
        val initialSize = repository.getAlarmItems().first().size
        val newAlarmItem = AlarmItem(
            time = Calendar.getInstance(),
            daysOfWeek = listOf(DayOfWeek.SUNDAY),
            message = "new alarm",
            isEnabled = true,
            id = 3
        )

        viewModel.addAlarmItem(newAlarmItem)
        val updatedSize = repository.getAlarmItems().first().size
        assertEquals(initialSize + 1, updatedSize)
    }

    @Test
    fun viewModel_removeAlarmItem_removesAlarm() = runTest {
        val initialSize = repository.getAlarmItems().first().size
        val alarmItem = repository.getAlarmItemById(2)

        viewModel.removeAlarmItem(alarmItem)
        val updatedSize = repository.getAlarmItems().first().size
        assertEquals(initialSize - 1, updatedSize)
    }

    @Test
    fun viewModel_changeDayOfWeek_addsDayToSelectedItem() = runTest {
        val alarmItem = repository.getAlarmItemById(1)
        viewModel.selectAlarmItem(alarmItem)

        val initialDaysOfWeek = alarmItem.daysOfWeek.size
        viewModel.changeDaysOfWeek(DayOfWeek.WEDNESDAY)

        val updatedDaysOfWeek = viewModel.alarmState.first { it.selectedItem != null }
        val selectedDays = updatedDaysOfWeek.selectedItem?.daysOfWeek ?: emptyList()

        assertEquals(initialDaysOfWeek + 1, selectedDays.size)
        assertTrue(selectedDays.contains(DayOfWeek.WEDNESDAY))
    }

    @Test
    fun viewModel_changeDayOfWeek_addsDayFromSelectedItem() = runTest {
        val alarmItem = repository.getAlarmItemById(1)
        viewModel.selectAlarmItem(alarmItem)

        val existedDayOfWeek = alarmItem.daysOfWeek.first()
        val initialDaysOfWeek = alarmItem.daysOfWeek.size
        viewModel.changeDaysOfWeek(existedDayOfWeek)

        val updatedDaysOfWeek = viewModel.alarmState.first { it.selectedItem != null }
        val selectedDays = updatedDaysOfWeek.selectedItem?.daysOfWeek ?: emptyList()

        assertEquals(initialDaysOfWeek - 1, selectedDays.size)
        assertFalse(selectedDays.contains(existedDayOfWeek))
    }

    @Test
    fun viewModel_updateAlarm_updatesAlarm() = runTest {
        val alarmItemId = 1
        val alarmItem = repository.getAlarmItemById(alarmItemId)
        val updatedAlarmItem = alarmItem.copy(message = "updated message")

        viewModel.updateAlarm(updatedAlarmItem)
        val retrievedAlarmItem = repository.getAlarmItemById(alarmItemId)
        assertEquals(updatedAlarmItem.message, retrievedAlarmItem.message)
    }
}