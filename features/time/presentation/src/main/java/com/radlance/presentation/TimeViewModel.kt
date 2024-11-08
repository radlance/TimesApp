package com.radlance.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.ZonedDateTime
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone

class TimeViewModel : ViewModel() {
    private val _timeUiState = MutableStateFlow(TimeUiState())

    val timeUiState: StateFlow<TimeUiState>
        get() = _timeUiState

    private var updateJob: Job? = null

    fun startUpdatingTime() {
        updateJob = viewModelScope.launch {
            while (isActive) {
                updateTime()
                delay(1000)
            }
        }
    }

    fun stopUpdatingTime() {
        updateJob?.cancel()
    }

    private fun updateTime() {
        val timeZone = TimeZone.getDefault()
        val calendar = Calendar.getInstance(timeZone)
        val currentTime = calendar.time

        val sdf = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
        val formattedTime = sdf.format(currentTime)

        val (hour, minute, seconds) = formattedTime.split(" ", ":")

        _timeUiState.update { currentState ->
            currentState.copy(
                hour = hour.toInt(),
                minute = minute.toInt(),
                seconds = seconds.toInt(),
                timeZone = ZonedDateTime.now().zone.toString()
                    .replace("_", " ")
                    .replace("/", ", ")
            )
        }
    }
}