package com.radlance.presentation

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StopwatchViewModel @Inject constructor(
    private val stopwatchService: StopwatchServiceInterface
) : ViewModel() {

    private val _elapsedTimeState = MutableStateFlow(0L)
    val elapsedTimeState: StateFlow<Long>
        get() = _elapsedTimeState.asStateFlow()

    init {
        viewModelScope.launch {
            stopwatchService.getElapsedTime().observeForever {
                _elapsedTimeState.value = it
            }
        }
    }

    fun commandService(context: Context, servicestate: SERVICESTATE) {
        stopwatchService.commandService(context, servicestate)
    }
}