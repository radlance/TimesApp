package com.radlance.presentation

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StopwatchViewModel @Inject constructor(
    private val stopwatchService: StopwatchServiceInterface
) : ViewModel() {

    private val _stopwatchState = MutableStateFlow(StopwatchUiState())
    val stopwatchState: StateFlow<StopwatchUiState>
        get() = _stopwatchState.asStateFlow()

    init {
        viewModelScope.launch {
            stopwatchService.getElapsedTime().observeForever {

                _stopwatchState.update { currentState ->
                    currentState.copy(elapsedTime = it)
                }
            }
        }

        viewModelScope.launch {
            stopwatchService.getEnabledStatus().observeForever {
                _stopwatchState.update { currentState ->
                    currentState.copy(isEnabled = it)
                }
            }
        }
    }

    fun commandService(context: Context, serviceState: SERVICESTATE) {
        stopwatchService.commandService(context, serviceState)
    }
}