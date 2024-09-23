package com.radlance.timesapp.services

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.radlance.presentation.TimerAdditionalAction
import com.radlance.time.core.ServiceState
import com.radlance.timesapp.MainActivity
import com.radlance.timesapp.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class CountdownTimerService @Inject constructor() : LifecycleService(),
    TimerAdditionalAction {

    private lateinit var notificationManager: NotificationManager
    private var initialMilliSeconds = 10000L

    override fun onCreate() {
        super.onCreate()
        setupNotification()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.action?.let { action ->
            when (action) {
                ServiceState.START_OR_RESUME.name -> {
                    startCountdownTimer()
                    startForeground(
                        NOTIFICATION_ID,
                        getNotification(
                            getString(R.string.countdown_running),
                            formatMillisToTimer(initialMilliSeconds)
                        )
                    )
                }

                ServiceState.PAUSE.name -> {
                    _isTracking.value = false
                    notificationManager.notify(
                        NOTIFICATION_ID,
                        getNotification(
                            getString(R.string.pause),
                            formatMillisToTimer(_remainingMilliSeconds.value)
                        )
                    )
                }

                ServiceState.RESET.name -> {
                    resetCountdownTimer()
                    stopForeground(STOP_FOREGROUND_REMOVE)
                    stopSelf()
                }
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

    override fun setCountDownTime(time: Long) {
        _remainingMilliSeconds.value = time
    }

    override fun getCurrentTime(): Flow<Long> {
        return remainingMilliSeconds
    }

    override fun getEnabledStatus(): Flow<Boolean> {
        return isTracking
    }

    override fun commandService(context: Context, serviceState: ServiceState) {
        val intent = Intent(context, CountdownTimerService::class.java)
        intent.action = serviceState.name
        context.startService(intent)
    }

    private fun startCountdownTimer() {
        initialMilliSeconds = _remainingMilliSeconds.value
        _isTracking.value = true
        lifecycleScope.launch(Dispatchers.IO) {
            val endTimeMillis = System.currentTimeMillis() + initialMilliSeconds
            while (_isTracking.value && _remainingMilliSeconds.value > 0) {
                _remainingMilliSeconds.emit(endTimeMillis - System.currentTimeMillis())
                val seconds = TimeUnit.MILLISECONDS.toSeconds(_remainingMilliSeconds.value)
                if (_remainingSeconds.value != seconds) {
                    _remainingSeconds.emit(seconds)
                }
                delay(10)
            }
            if (_remainingMilliSeconds.value <= 0) {
                resetCountdownTimer()
                stopForeground(STOP_FOREGROUND_REMOVE)
                stopSelf()
            }
        }
    }

    private fun resetCountdownTimer() {
        _isTracking.value = false
        _remainingMilliSeconds.value = 10000L
        _remainingSeconds.value = 10000L
        initialMilliSeconds = 10000L
        lifecycleScope.coroutineContext.cancelChildren()
    }

    private fun setupNotification() {
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        _remainingSeconds.observe(this) { remainingSeconds ->
            if (_isTracking.value) {
                notificationManager.notify(
                    NOTIFICATION_ID,
                    getNotification(
                        getString(R.string.countdown_running),
                        formatMillisToTimer(TimeUnit.SECONDS.toMillis(remainingSeconds))
                    )
                )
            }
        }
    }

    private fun getNotification(title: String, text: String): Notification {
        val notificationChannel = NotificationChannel(
            NOTIFICATION_CHANNEL_ID,
            NOTIFICATION_CHANNEL_NAME,
            NotificationManager.IMPORTANCE_LOW
        )
        notificationManager.createNotificationChannel(notificationChannel)

        val intent = Intent(this, MainActivity::class.java).apply {
            putExtra("EXTRA_SCREEN", "COUNTDOWN_TIMER")
        }

        val pendingIntent = PendingIntent.getActivity(
            this,
            1,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE
        )

        val stopIntent = Intent(this, CountdownTimerService::class.java).also {
            it.action = ServiceState.RESET.name
        }

        val pendingStopIntent = PendingIntent.getService(
            this,
            3,
            stopIntent,
            PendingIntent.FLAG_MUTABLE
        )

        return NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
            .setOngoing(true)
            .setAutoCancel(false)
            .setSmallIcon(R.drawable.ic_timer)
            .setContentTitle(title)
            .setContentText(text)
            .setContentIntent(pendingIntent)
            .addAction(
                R.drawable.ic_timer,
                if (_isTracking.value) {
                    getString(R.string.pause)
                } else {
                    getString(R.string.resume)
                },
                PendingIntent.getService(
                    this,
                    2,
                    Intent(
                        this,
                        CountdownTimerService::class.java
                    ).also {
                        it.action = if (_isTracking.value) {
                            ServiceState.PAUSE.name
                        } else {
                            ServiceState.START_OR_RESUME.name
                        }
                    },
                    PendingIntent.FLAG_MUTABLE
                )
            )
            .addAction(
                R.drawable.ic_timer,
                getString(R.string.stop),
                pendingStopIntent
            )
            .build()
    }

    private fun <T> Flow<T>.observe(
        lifecycleOwner: LifecycleOwner,
        state: Lifecycle.State = Lifecycle.State.STARTED,
        observer: (T) -> Unit
    ) {
        lifecycleOwner.lifecycleScope.launch {
            flowWithLifecycle(lifecycleOwner.lifecycle, state).collect { value ->
                observer(value)
            }
        }
    }

    companion object {
        const val NOTIFICATION_ID = 173
        const val NOTIFICATION_CHANNEL_ID = "474"
        const val NOTIFICATION_CHANNEL_NAME = "countdown_timer_channel"

        private val _isTracking = MutableStateFlow(false)
        val isTracking: StateFlow<Boolean> = _isTracking.asStateFlow()

        private val _remainingSeconds = MutableStateFlow(10000L)

        private val _remainingMilliSeconds = MutableStateFlow(10000L)
        val remainingMilliSeconds: StateFlow<Long> = _remainingMilliSeconds.asStateFlow()
    }
}