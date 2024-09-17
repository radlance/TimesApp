package com.radlance.presentation

import android.location.Geocoder
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.FusedLocationProviderClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.ZonedDateTime
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone
import javax.inject.Inject

@HiltViewModel
class TimeViewModel @Inject constructor(
    private val fusedClient: FusedLocationProviderClient,
    private val geocoder: Geocoder
) : ViewModel() {
    private val _timeUiState = MutableStateFlow(TimeUiState())

    val timeUiState: StateFlow<TimeUiState>
        get() = _timeUiState

    fun startUpdatingTime() {
        viewModelScope.launch {
            while (true) {
                updateTime()
                delay(1000)
            }
        }
    }

    private fun updateTime() {
        val timeZone = TimeZone.getDefault()
        val calendar = Calendar.getInstance(timeZone)
        val currentTime = calendar.time

        val sdf = SimpleDateFormat("hh:mm:ss", Locale.getDefault())
        val formattedTime = sdf.format(currentTime)

        val (hour, minute, seconds) = formattedTime.split(" ", ":")

        _timeUiState.update { currentState ->
            currentState.copy(
                hour = hour.toInt(),
                minute = minute.toInt(),
                seconds = seconds.toInt(),
                timeZone = ZonedDateTime.now().zone.toString()
            )
        }
    }
}