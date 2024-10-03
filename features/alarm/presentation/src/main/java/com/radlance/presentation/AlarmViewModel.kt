package com.radlance.presentation

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class AlarmViewModel @Inject constructor(
    private val alarmScheduler: AlarmScheduler
) : ViewModel() {
    private val _alarmState = MutableStateFlow(List(3) {
        AlarmItem(
            id = it,
            time = Calendar.getInstance(),
            message = it.toString(),
            isEnabled = false
        )
    })

    val alarmState: StateFlow<List<AlarmItem>>
        get() = _alarmState

    fun switchAlarmState(alarmItem: AlarmItem, enabled: Boolean) {
        _alarmState.update { alarmList ->
            alarmList.map {
                if (it.id == alarmItem.id) it.copy(isEnabled = enabled) else it
            }
        }
    }

    fun schedule(alarmItem: AlarmItem) {
        alarmScheduler.schedule(alarmItem)
    }

    fun cancel(alarmItem: AlarmItem) {
        alarmScheduler.cancel(alarmItem)
    }
}