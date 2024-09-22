package com.radlance.timesapp.services.stopwatch

import java.util.Locale
import java.util.concurrent.TimeUnit

fun formatMillisToTimer(ms: Long,includeMillis: Boolean = false): String {
    var millis = ms

    val hours = TimeUnit.MILLISECONDS.toHours(millis)
    millis -= TimeUnit.HOURS.toMillis(hours)
    val minutes = TimeUnit.MILLISECONDS.toMinutes(millis)
    millis -= TimeUnit.MINUTES.toMillis(minutes)
    val seconds = TimeUnit.MILLISECONDS.toSeconds(millis)
    millis -= TimeUnit.SECONDS.toMillis(seconds)


    return if(includeMillis){
        String.format(Locale.getDefault(), "%02d:%02d:%02d:%03d",hours, minutes,seconds,millis)
    }else{
        String.format(Locale.getDefault(), "%02d:%02d:%02d",hours, minutes,seconds)
    }
}