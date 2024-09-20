package com.radlance.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class StopWatchViewModel : ViewModel() {
    private var isTimerRunning = false
    private var timerJob: Job? = null
    private var elapsedTime: Long = 0
    private var startTime: Long = 0
    private var pauseTime: Long = 0

    private val _elapsedTimeState = MutableStateFlow(StopwatchUiState())
    val elapsedTimeState: StateFlow<StopwatchUiState>
        get() = _elapsedTimeState.asStateFlow()

    fun startStopwatch() {
        if (!isTimerRunning) {
            isTimerRunning = true
            if (pauseTime > 0) {
                startTime = System.currentTimeMillis() - pauseTime
                pauseTime = 0
            } else {
                startTime = System.currentTimeMillis()
            }
            timerJob = CoroutineScope(Dispatchers.Main).launch {
                while (isTimerRunning) {
                    delay(10)
                    val currentTime = System.currentTimeMillis()
                    elapsedTime = currentTime - startTime
                    formatTime(elapsedTime)
                }
            }
        }
    }

    fun pauseStopwatch() {
        isTimerRunning = false
        pauseTime = elapsedTime
        timerJob?.cancel()
    }

    fun resetStopwatch() {
        isTimerRunning = false
        timerJob?.cancel()
        elapsedTime = 0
        pauseTime = 0
        formatTime(elapsedTime)
    }

    private fun formatTime(time: Long) {
        _elapsedTimeState.update { currentState ->
            currentState.copy(
                milliSeconds = (time % 1000) / 10,
                seconds = (time / 1000) % 60,
                minutes = (time / (1000 * 60)) % 60,
                hours = time / (1000 * 60 * 60)
            )
        }
    }

    override fun onCleared() {
        super.onCleared()
        timerJob?.cancel()
    }
}
