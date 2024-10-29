package com.radlance.timesapp.services

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.text.format.DateFormat
import android.widget.Toast
import com.radlance.domain.AlarmItem
import com.radlance.presentation.AlarmScheduler
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

class AndroidAlarmScheduler @Inject constructor(
    private val context: Context
): AlarmScheduler {

    private val alarmManager = context.getSystemService(AlarmManager::class.java)

    override fun schedule(alarmItem: AlarmItem) {
        val intent = Intent(context, AlarmReceiver::class.java).apply {
            putExtra("extraAlarmItemId", alarmItem.id)
        }

        alarmItem.daysOfWeek.forEach { dayOfWeek ->
            val calendar = Calendar.getInstance().apply {
                timeInMillis = alarmItem.time.timeInMillis
                set(Calendar.DAY_OF_WEEK, dayOfWeek.value + 1)
                if (timeInMillis < System.currentTimeMillis()) {
                    add(Calendar.WEEK_OF_YEAR, 1)
                }
            }
            val is24HourFormat = DateFormat.is24HourFormat(context)
            val pattern = if(is24HourFormat) "dd.MM.yyyy HH:mm:ss" else "dd.MM.yyyy hh:mm:ss a"
            val sdf = SimpleDateFormat(pattern, Locale.getDefault())
            val formattedTime = sdf.format(calendar.time.time)

            Toast.makeText(context, "scheduled: $formattedTime", Toast.LENGTH_LONG).show()


            alarmManager.setExact(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                PendingIntent.getBroadcast(
                    context,
                    alarmItem.id + dayOfWeek.value,
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                )
            )
        }
    }

    override fun cancel(alarmItem: AlarmItem) {

        for (day in alarmItem.daysOfWeek) {
            alarmManager.cancel(
                PendingIntent.getBroadcast(
                    context,
                    alarmItem.id + day.value,
                    Intent(context, AlarmReceiver::class.java),
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                )
            )
        }
    }
}