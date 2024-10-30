package com.radlance.timesapp.services

import android.content.Intent
import android.media.MediaPlayer
import android.media.RingtoneManager
import androidx.lifecycle.LifecycleService

class AlarmSoundService : LifecycleService() {

    private var mediaPlayer: MediaPlayer? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(
                this,
                RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
            ).apply {
                isLooping = true
                start()
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.let {
            if (it.isPlaying) {
                it.stop()
                it.release()
                mediaPlayer = null
            }
        }
    }
}