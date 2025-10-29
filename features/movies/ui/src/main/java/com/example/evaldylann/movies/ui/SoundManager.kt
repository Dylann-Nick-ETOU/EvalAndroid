package com.example.evaldylann.movies.ui


import android.content.Context
import android.media.AudioAttributes
import android.media.SoundPool
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.evaldylann.features.movies.ui.R

class SoundManager(context: Context) {
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private val pool = SoundPool.Builder()
        .setMaxStreams(1)
        .setAudioAttributes(
            AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                .build()
        ).build()

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private val soundId = pool.load(context, R.raw.click, 1)
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun playClick() { pool.play(soundId, 1f, 1f, 1, 0, 1f) }
}
