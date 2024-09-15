package com.radlance.presentation

import android.annotation.SuppressLint
import android.location.Geocoder
import android.os.Build
import androidx.lifecycle.ViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone
import javax.inject.Inject

@HiltViewModel
class TimeViewModel @Inject constructor(
    private val fusedClient: FusedLocationProviderClient,
    private val geocoder: Geocoder
) : ViewModel() {
    private var job: Job? = null

    private val _timeUiState = MutableStateFlow(TimeUiState())
    val timeUiState: StateFlow<TimeUiState>
        get() = _timeUiState


    fun startUpdatingTime() {
        job = CoroutineScope(Dispatchers.Main).launch {
            while (true) {
                updateTime()
                delay(1000)
            }
        }
    }

    fun stopUpdatingTime() {
        job?.cancel()
        job = null
    }

    private fun updateTime() {
        val timeZone = TimeZone.getDefault()
        val calendar = Calendar.getInstance(timeZone)
        val currentTime = calendar.time

        val sdf = SimpleDateFormat("hh:mm:ss a", Locale.getDefault())
        val formattedTime = sdf.format(currentTime)

        val (hour, minute, seconds, amOrPm) = formattedTime.split(" ", ":")

        _timeUiState.update { currentState ->
            currentState.copy(
                hour = hour.toInt(),
                minute = minute.toInt(),
                seconds = seconds.toInt(),
                amOrPm = amOrPm
            )
        }
    }

    @SuppressLint("MissingPermission")
    fun getLocation() {
        fusedClient.lastLocation.addOnSuccessListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                geocoder.getFromLocation(it.latitude, it.longitude, 1) { addresses ->
                    addresses.first().apply {
                        _timeUiState.update { currentState ->
                            currentState.copy(
                                country = countryName,
                                city = locality
                            )
                        }
                    }
                }
            } else {
                @Suppress("DEPRECATION")
                geocoder.getFromLocation(it.latitude, it.longitude, 1)?.first()?.apply {
                    _timeUiState.update { currentState ->
                        currentState.copy(
                            country = countryName,
                            city = locality
                        )
                    }
                }
            }
            updateTime()
        }
    }
}