package com.radlance.timesapp.services

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LifecycleService
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
    private var initialMilliSeconds = 0L

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
                            formatMillis(initialMilliSeconds)
                        )
                    )
                }

                ServiceState.PAUSE.name -> {
                    _isTracking.value = false
                    notificationManager.notify(
                        NOTIFICATION_ID,
                        getNotification(
                            getString(R.string.countdown_paused),
                            formatMillis(_remainingMilliSeconds.value)
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
        _initialTime.value = time
    }

    override fun getInitialTime(): Flow<Long> {
        return initialTime
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
                notificationManager.cancel(NOTIFICATION_ID)
                stopSelf()

                showFinishNotification()
            }
        }
    }
    private fun showFinishNotification() {
        val notificationChannel = NotificationChannel(
            FINISH_NOTIFICATION_CHANNEL_ID,
            FINISH_NOTIFICATION_CHANNEL_NAME,
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            setSound(
                Uri.parse(
                    "android.resource://" + packageName + "/" + R.raw.timer
                ), null
            )
        }

        notificationManager.createNotificationChannel(notificationChannel)

        val builder = NotificationCompat.Builder(this, FINISH_NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_timer)
            .setContentTitle(getString(R.string.time_over))
            .setContentText(getString(R.string.timer_has_finished))
            .setOngoing(true)

        notificationManager.createNotificationChannel(notificationChannel)
        notificationManager.notify(NOTIFICATION_ID, builder.build())
    }

    private fun resetCountdownTimer() {
        _isTracking.value = false
        _remainingMilliSeconds.value = 0
        _remainingSeconds.value = 0
        initialMilliSeconds = 0
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
                        formatMillis(TimeUnit.SECONDS.toMillis(remainingSeconds))
                    )
                )
            }
        }
    }

    private fun getNotification(title: String, text: String): Notification {
        val notificationChannel = NotificationChannel(
            NOTIFICATION_CHANNEL_ID,
            NOTIFICATION_CHANNEL_NAME,
            NotificationManager.IMPORTANCE_DEFAULT
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
            .setOnlyAlertOnce(true)
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

    companion object {
        const val NOTIFICATION_ID = 1
        const val NOTIFICATION_CHANNEL_ID = "1"
        const val FINISH_NOTIFICATION_CHANNEL_ID = "2"
        const val NOTIFICATION_CHANNEL_NAME = "timer channel"
        const val FINISH_NOTIFICATION_CHANNEL_NAME = "timer finish channel"

        private val _isTracking = MutableStateFlow(false)
        val isTracking: StateFlow<Boolean> = _isTracking.asStateFlow()

        private val _remainingSeconds = MutableStateFlow(0L)

        private val _remainingMilliSeconds = MutableStateFlow(0L)
        val remainingMilliSeconds: StateFlow<Long> = _remainingMilliSeconds.asStateFlow()

        private val _initialTime = MutableStateFlow(0L)
        val initialTime: StateFlow<Long> = _initialTime
    }
}
