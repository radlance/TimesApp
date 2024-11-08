package com.radlance.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.radlance.domain.AlarmItem
import com.radlance.domain.AlarmRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import javax.inject.Inject

@HiltViewModel
class AlarmViewModel @Inject constructor(
    private val alarmScheduler: AlarmScheduler,
    private val alarmRepository: AlarmRepository
) : ViewModel() {

    private val alarmItems = alarmRepository.getAlarmItems().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = null
    )

    private val _alarmState = MutableStateFlow(AlarmUiState())

    val alarmState: StateFlow<AlarmUiState> = combine(
        alarmItems,
        _alarmState
    ) { items, state ->
        state.copy(alarmItems = items)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = AlarmUiState()
    )

    fun switchAlarmState(alarmItem: AlarmItem, enabled: Boolean) {
        viewModelScope.launch {
            alarmRepository.updateAlarmItem(alarmItem.copy(isEnabled = enabled))
            if (enabled) {
                alarmScheduler.schedule(alarmItem)
            } else {
                alarmScheduler.cancel(alarmItem)
            }
        }
    }

    fun addAlarmItem(alarmItem: AlarmItem) {
        viewModelScope.launch {
            alarmRepository.addAlarmItem(alarmItem)
            val lastAlarmItem = async {
                alarmRepository.getLastAlarmItem()
            }.await()

            alarmScheduler.schedule(lastAlarmItem)
        }
    }

    fun removeAlarmItem(alarmItem: AlarmItem) {
        viewModelScope.launch {
            alarmRepository.deleteAlarmItem(alarmItem)
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
        viewModelScope.launch {
            alarmScheduler.cancel(alarmRepository.getAlarmItemById(alarmItem.id))
            alarmRepository.updateAlarmItem(alarmItem)
            if (alarmItem.isEnabled) {
                alarmScheduler.schedule(alarmItem)
            }
        }
    }

    fun selectAlarmItem(alarmItem: AlarmItem) {
        _alarmState.update { currentState ->
            currentState.copy(selectedItem = alarmItem)
        }
    }

}