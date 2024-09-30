package com.radlance.presentation

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class AlarmViewModel @Inject constructor(
    private val alarmScheduler: AlarmScheduler
) : ViewModel() {
    fun schedule(calendar: Calendar, message: String) {
        alarmScheduler.schedule(AlarmItem(calendar, message))
    }

    fun cancel(calendar: Calendar) {
        alarmScheduler.cancel(AlarmItem(calendar, ""))
    }
}