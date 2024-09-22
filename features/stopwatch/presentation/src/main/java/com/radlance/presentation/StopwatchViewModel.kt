package com.radlance.presentation

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class StopwatchViewModel @Inject constructor(
    private val stopwatchService: StopwatchServiceInterface
) : ViewModel() {

    val stopwatchState = combine(
        stopwatchService.getElapsedTime(),
        stopwatchService.getEnabledStatus()
    ) { elapsedTime, isEnabled ->
        StopwatchUiState(elapsedTime = elapsedTime, isEnabled = isEnabled)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = StopwatchUiState()
    )

    fun commandService(context: Context, serviceState: SERVICESTATE) {
        stopwatchService.commandService(context, serviceState)
    }
}