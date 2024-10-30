package com.radlance.timesapp.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.radlance.database.TimesDao
import com.radlance.timesapp.MainActivity
import com.radlance.timesapp.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class AlarmReceiver : BroadcastReceiver() {

    @Inject
    lateinit var dao: TimesDao

    override fun onReceive(context: Context?, intent: Intent?) {
        val serviceIntent = Intent(context, AlarmSoundService::class.java)

        if (intent?.action == INTENT_FLAG_CLOSE) {
            context?.stopService(serviceIntent)
            val notificationManager =
                context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.cancel(NOTIFICATION_ID)
        } else {
            showNotification(context)
            context?.startService(serviceIntent)

            val alarmItemId = intent?.getIntExtra("extraAlarmItemId", -1) ?: -1
            CoroutineScope(Dispatchers.IO).launch {
                val alarmItem = dao.getAlarmItemById(alarmItemId)
                val incrementedTriggeredDayCount = alarmItem.currentCountOfTriggering.inc()

                if (alarmItem.daysOfWeek.size == incrementedTriggeredDayCount) {
                    dao.disableAlarmById(alarmItemId)
                } else {
                    dao.updateAlarmItem(
                        alarmItem.copy(currentCountOfTriggering = incrementedTriggeredDayCount)
                    )
                }
            }
        }
    }

    private fun showNotification(context: Context?) {
        val notificationManager =
            context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val intent = Intent(context, MainActivity::class.java).apply {
            putExtra("EXTRA_SCREEN", "ALARM")
            flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
        }
        val pendingIntent = PendingIntent.getActivity(
            context,
            5,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE
        )

        val closeIntent = Intent(context, AlarmReceiver::class.java).apply {
            action = INTENT_FLAG_CLOSE
        }
        val closePendingIntent = PendingIntent.getBroadcast(
            context,
            0,
            closeIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE
        )

        val importance = NotificationManager.IMPORTANCE_HIGH

        val mChannel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance)
        notificationManager.createNotificationChannel(mChannel)

        val mBuilder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_alarm)
            .setContentTitle("Alarm triggered")
            .setContentText("Wake up!")
            .setContentIntent(pendingIntent)
            .setOngoing(true)
            .setAutoCancel(false)
            .addAction(R.drawable.ic_alarm, "Close", closePendingIntent)

        notificationManager.notify(NOTIFICATION_ID, mBuilder.build())
    }

    private companion object {
        const val INTENT_FLAG_CLOSE = "CLOSE_NOTIFICATION"
        const val NOTIFICATION_ID = 5
        const val CHANNEL_ID = "5"
        const val CHANNEL_NAME = "Alarm channel"
    }
}