package com.radlance.timesapp.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import androidx.core.app.NotificationCompat
import com.radlance.timesapp.R

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        showNotification(context)
    }

    private fun showNotification(context: Context?) {
        val notificationManager =
            context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager


        val importance = NotificationManager.IMPORTANCE_HIGH

        val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
        val mChannel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance).apply {
            setSound(
                alarmSound,
                null
            )
        }

        notificationManager.createNotificationChannel(mChannel)

        val mBuilder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_alarm)
            .setContentTitle("Alarm triggered")
            .setContentText("Wake up!")


        notificationManager.notify(NOTIFICATION_ID, mBuilder.build())
    }

    private companion object {
        const val NOTIFICATION_ID = 5
        const val CHANNEL_ID = "5"
        const val CHANNEL_NAME = "Alarm channel"
    }
}