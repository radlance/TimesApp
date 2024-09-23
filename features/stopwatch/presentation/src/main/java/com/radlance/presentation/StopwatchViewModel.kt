package com.radlance.presentation

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.radlance.time.core.TimeServiceAction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class StopwatchViewModel @Inject constructor(
    private val stopwatchService: TimeServiceAction
) : ViewModel() {

    val stopwatchState = combine(
        stopwatchService.getCurrentTime(),
        stopwatchService.getEnabledStatus()
    ) { elapsedTime, isEnabled ->
        StopwatchUiState(elapsedTime = elapsedTime, isEnabled = isEnabled)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = StopwatchUiState()
    )

    fun commandService(context: Context, serviceState: com.radlance.time.core.ServiceState) {
        stopwatchService.commandService(context, serviceState)
    }
}