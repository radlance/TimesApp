package com.radlance.presentation

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AlarmViewModel @Inject constructor(
    private val alarmScheduler: AlarmScheduler
) : ViewModel() {
    fun schedule(alarmItem: AlarmItem) {
        alarmScheduler.schedule(alarmItem)
    }

    fun cancel(alarmItem: AlarmItem) {
        alarmScheduler.cancel(alarmItem)
    }
}