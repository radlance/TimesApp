package com.radlance.timesapp.services

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.lifecycleScope
import com.radlance.time.core.ServiceState
import com.radlance.time.core.TimeServiceAction
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

class StopwatchService @Inject constructor() : LifecycleService(),
    TimeServiceAction {

    private lateinit var notificationManager: NotificationManager
    private var elapsedMillisBeforePause = 0L

    override fun onCreate() {
        super.onCreate()
        setupNotification()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.action?.let { action ->
            when (action) {
                ServiceState.START_OR_RESUME.name -> {
                    startResumeStopWatch()
                    startForeground(
                        NOTIFICATION_ID,
                        getNotification(
                            getString(R.string.stopwatch_running),
                            formatMillis(elapsedMillisBeforePause)
                        )
                    )
                }

                ServiceState.PAUSE.name -> {
                    _isTracking.value = false
                    elapsedMillisBeforePause = _elapsedMilliSeconds.value
                    notificationManager.notify(
                        NOTIFICATION_ID,
                        getNotification(
                            getString(R.string.stopwatch_paused),
                            formatMillis(elapsedMillisBeforePause)
                        )
                    )
                }

                ServiceState.RESET.name -> {
                    resetStopWatch()
                    stopForeground(STOP_FOREGROUND_REMOVE)
                    stopSelf()
                }
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

    override fun getCurrentTime(): Flow<Long> {
        return elapsedMilliSeconds
    }

    override fun getEnabledStatus(): Flow<Boolean> {
        return isTracking
    }

    override fun commandService(context: Context, serviceState: ServiceState) {
        val intent = Intent(context, StopwatchService::class.java)
        intent.action = serviceState.name
        context.startService(intent)
    }

    private fun startResumeStopWatch() {
        _isTracking.value = true
        lifecycleScope.launch(Dispatchers.IO) {
            val startTimeMillis = System.currentTimeMillis()
            while (_isTracking.value) {
                _elapsedMilliSeconds.emit((System.currentTimeMillis() - startTimeMillis) + elapsedMillisBeforePause)
                val seconds = TimeUnit.MILLISECONDS.toSeconds(_elapsedMilliSeconds.value)
                if (_elapsedSeconds.value != seconds) {
                    _elapsedSeconds.emit(seconds)
                }
                delay(10)
            }
        }
    }

    private fun resetStopWatch() {
        _isTracking.value = false
        _elapsedMilliSeconds.value = 0L
        _elapsedSeconds.value = 0L
        elapsedMillisBeforePause = 0L
        lifecycleScope.coroutineContext.cancelChildren()
    }

    private fun setupNotification() {
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        _elapsedSeconds.observe(this) { elapsedSeconds ->
            if (_isTracking.value) {
                notificationManager.notify(
                    NOTIFICATION_ID,
                    getNotification(
                        getString(R.string.stopwatch_running),
                        formatMillis(TimeUnit.SECONDS.toMillis(elapsedSeconds))
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
            putExtra("EXTRA_SCREEN", "STOPWATCH")
            flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
        }

        val pendingIntent = PendingIntent.getActivity(
            this,
            1,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE
        )

        val stopIntent = Intent(this, StopwatchService::class.java).also {
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
            .setOnlyAlertOnce(true)
            .setAutoCancel(false)
            .setSmallIcon(R.drawable.ic_stopwatch)
            .setContentTitle(title)
            .setContentText(text)
            .setContentIntent(pendingIntent)
            .addAction(
                R.drawable.ic_stopwatch,
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
                        StopwatchService::class.java
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
                R.drawable.ic_stopwatch,
                getString(R.string.stop),
                pendingStopIntent
            )
            .build()
    }

    companion object {
        const val NOTIFICATION_ID = 2
        const val NOTIFICATION_CHANNEL_ID = "3"
        const val NOTIFICATION_CHANNEL_NAME = "stopwatch channel"

        private val _isTracking = MutableStateFlow(false)
        val isTracking: StateFlow<Boolean> = _isTracking.asStateFlow()

        private val _elapsedSeconds = MutableStateFlow(0L)

        private val _elapsedMilliSeconds = MutableStateFlow(0L)
        val elapsedMilliSeconds: StateFlow<Long> = _elapsedMilliSeconds.asStateFlow()
    }
}


