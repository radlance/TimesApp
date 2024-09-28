package com.radlance.presentation

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.radlance.time.core.ServiceState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class CountdownTimerViewModel @Inject constructor(
    private val countdownTimerService: TimerAdditionalAction
) : ViewModel() {

    val countdownTimerState = combine(
        countdownTimerService.getCurrentTime(),
        countdownTimerService.getEnabledStatus(),
        countdownTimerService.getInitialTime()
    ) { remainingTime, isEnabled, initialTime ->
        CountdownTimerUiState(
            remainingTime = remainingTime,
            initialTime = initialTime,
            isEnabled = isEnabled
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = CountdownTimerUiState()
    )

    fun commandService(context: Context, serviceState: ServiceState) {
        countdownTimerService.commandService(context, serviceState)
    }

    fun setCountDownTimer(time: Long) {
        countdownTimerService.setCountDownTime(time)
    }
}