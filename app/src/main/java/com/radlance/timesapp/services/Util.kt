package com.radlance.timesapp.services

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.util.Locale
import java.util.concurrent.TimeUnit

fun formatMillis(ms: Long, includeMillis: Boolean = false): String {
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

inline fun <T> Flow<T>.observe(
    lifecycleOwner: LifecycleOwner,
    state: Lifecycle.State = Lifecycle.State.STARTED,
    crossinline observer: (T) -> Unit
) {
    lifecycleOwner.lifecycleScope.launch {
        flowWithLifecycle(lifecycleOwner.lifecycle, state).collect { value ->
            observer(value)
        }
    }
}