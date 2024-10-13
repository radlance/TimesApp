package com.radlance.timesapp.services

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.radlance.domain.AlarmItem
import com.radlance.presentation.AlarmScheduler
import javax.inject.Inject

class AndroidAlarmScheduler @Inject constructor(
    private val context: Context
): AlarmScheduler {

    private val alarmManager = context.getSystemService(AlarmManager::class.java)

    override fun schedule(alarmItem: AlarmItem) {
        val intent = Intent(context, AlarmReceiver::class.java)

        Toast.makeText(context, "scheduled: ${alarmItem.time.time}", Toast.LENGTH_SHORT).show()
        alarmManager.setExact(
            AlarmManager.RTC_WAKEUP,
            alarmItem.time.timeInMillis,
            PendingIntent.getBroadcast(
                context,
                alarmItem.id,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        )
    }

    override fun cancel(alarmItem: AlarmItem) {
        Toast.makeText(context, "canceled: ${alarmItem.time.time}", Toast.LENGTH_SHORT).show()
        alarmManager.cancel(
            PendingIntent.getBroadcast(
                context,
                alarmItem.id,
                Intent(context, AlarmReceiver::class.java),
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        )
    }
}