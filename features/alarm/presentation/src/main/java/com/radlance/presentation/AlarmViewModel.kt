package com.radlance.presentation

import androidx.lifecycle.ViewModel
import com.radlance.domain.AlarmItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.DayOfWeek
import javax.inject.Inject

@HiltViewModel
class AlarmViewModel @Inject constructor(
    private val alarmScheduler: AlarmScheduler
) : ViewModel() {
    private val _alarmState = MutableStateFlow(AlarmUiState())

    val alarmState: StateFlow<AlarmUiState>
        get() = _alarmState.asStateFlow()

    fun switchAlarmState(alarmItem: AlarmItem, enabled: Boolean) {
        _alarmState.update { currentState ->
            val updatedItems = currentState.alarmItems.map {
                if (it.id == alarmItem.id) it.copy(isEnabled = enabled) else it
            }

            currentState.copy(alarmItems = updatedItems)
        }
    }

    fun changeDaysOfWeek(dayOfWeek: DayOfWeek) {
        _alarmState.update { currentState ->
            val currentDaysOfWeek =
                currentState.selectedItem?.daysOfWeek?.toMutableList() ?: mutableListOf()

            if(currentDaysOfWeek.contains(dayOfWeek)) {
                currentDaysOfWeek.remove(dayOfWeek)
            } else {
                currentDaysOfWeek.add(dayOfWeek)
            }

            currentState.copy(
                selectedItem = currentState.selectedItem?.copy(daysOfWeek = currentDaysOfWeek)
            )

        }
    }

    fun updateAlarm(alarmItem: AlarmItem) {
        _alarmState.update { currentState ->
            currentState.copy(
                alarmItems = currentState.alarmItems.map {
                    if (it.id == alarmItem.id) alarmItem else it
                }
            )
        }
    }

    fun selectAlarmItem(alarmItem: AlarmItem) {
        _alarmState.update { currentState ->
            currentState.copy(selectedItem = alarmItem)
        }
    }

    fun schedule(alarmItem: AlarmItem) {
        alarmScheduler.schedule(alarmItem)
    }

    fun cancel(alarmItem: AlarmItem) {
        alarmScheduler.cancel(alarmItem)
    }
}