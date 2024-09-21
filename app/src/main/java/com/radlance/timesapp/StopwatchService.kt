package com.radlance.timesapp

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import com.radlance.presentation.SERVICESTATE
import com.radlance.presentation.StopwatchServiceInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class StopwatchService @Inject constructor() : LifecycleService(),
    StopwatchServiceInterface {

    private lateinit var notificationManager: NotificationManager
    private var elapsedMillisBeforePause = 0L

    override fun onCreate() {
        super.onCreate()
        setupNotification()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.action?.let { action ->
            when (action) {
                SERVICESTATE.START_OR_RESUME.name -> {
                    startResumeStopWatch()
                    startForeground(
                        NOTIFICATION_ID,
                        getNotification(
                            getString(R.string.stopwatch_running),
                            formatMillisToTimer(elapsedMillisBeforePause)
                        )
                    )
                }

                SERVICESTATE.PAUSE.name -> {
                    _isTracking.value = false
                    elapsedMillisBeforePause = _elapsedMilliSeconds.value!!
                    notificationManager.notify(
                        NOTIFICATION_ID,
                        getNotification(
                            getString(R.string.stopwatch_running),
                            formatMillisToTimer(elapsedMillisBeforePause)
                        )
                    )
                }

                SERVICESTATE.RESET.name -> {
                    resetStopWatch()
                    stopForeground(STOP_FOREGROUND_REMOVE)
                    stopSelf()
                }
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

    override fun getElapsedTime(): LiveData<Long> {
        return elapsedMilliSeconds
    }

    override fun getEnabledStatus(): LiveData<Boolean> {
        return isTracking
    }

    override fun commandService(context: Context, serviceState: SERVICESTATE) {
        val intent = Intent(context,StopwatchService::class.java)
        intent.action = serviceState.name
        context.startService(intent)
    }

    private fun startResumeStopWatch() {
        _isTracking.value = true
        lifecycleScope.launch(Dispatchers.IO) {
            val startTimeMillis = System.currentTimeMillis()
            while (_isTracking.value!!) {
                _elapsedMilliSeconds.postValue((System.currentTimeMillis() - startTimeMillis) + elapsedMillisBeforePause)
                val seconds = TimeUnit.MILLISECONDS.toSeconds(_elapsedMilliSeconds.value!!)
                if (_elapsedSeconds.value != seconds) {
                    _elapsedSeconds.postValue(seconds)
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
            if (_isTracking.value!!) {
                notificationManager.notify(
                    NOTIFICATION_ID,
                    getNotification(
                        getString(R.string.stopwatch_running),
                        formatMillisToTimer(TimeUnit.SECONDS.toMillis(elapsedSeconds))
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

        return NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
            .setOngoing(true)
            .setAutoCancel(false)
            .setSmallIcon(R.drawable.ic_stopwatch)
            .setContentTitle(title)
            .setContentText(text)
            .setContentIntent(
                PendingIntent.getActivity(
                    this,
                    1,
                    Intent(this, MainActivity::class.java),
                    PendingIntent.FLAG_MUTABLE
                )
            )
            .addAction(
                R.drawable.ic_stopwatch,
                if (_isTracking.value!!) getString(R.string.pause) else getString(R.string.resume),
                PendingIntent.getService(
                    this,
                    2,
                    Intent(
                        this,
                        StopwatchService::class.java
                    ).also {
                        it.action =
                            if (_isTracking.value!!) SERVICESTATE.PAUSE.name else SERVICESTATE.START_OR_RESUME.name
                    },
                    PendingIntent.FLAG_MUTABLE
                )
            )
            .build()
    }

    private fun commandService(serviceState: SERVICESTATE) {
        val intent = Intent(this, StopwatchService::class.java)
        intent.action = serviceState.name
        startService(intent)
    }

    companion object {
        const val NOTIFICATION_ID = 172
        const val NOTIFICATION_CHANNEL_ID = "473"
        const val NOTIFICATION_CHANNEL_NAME = "stopwatch_channel"

        private val _isTracking = MutableLiveData(false)
        val isTracking: LiveData<Boolean> = _isTracking

        private val _elapsedSeconds = MutableLiveData(0L)
        val elapsedSeconds: LiveData<Long> = _elapsedSeconds

        private val _elapsedMilliSeconds = MutableLiveData(0L)
        val elapsedMilliSeconds: LiveData<Long> = _elapsedMilliSeconds
    }
}

