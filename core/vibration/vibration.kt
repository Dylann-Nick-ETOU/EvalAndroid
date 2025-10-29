package com.example.evalmathieud2.core.system

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager

/**
 * Vibre l'appareil, si le matériel le permet.
 * @param durationMillis Durée de la vibration (ms).
 * @param amplitude Amplitude 1..255, ignorée si DEFAULT est utilisé.
 */
fun Context.vibrate(
    durationMillis: Long = 60L,
    amplitude: Int = VibrationEffect.DEFAULT_AMPLITUDE // laisse l’OS choisir
) {
    val vibrator: Vibrator? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        (getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as? VibratorManager)?.defaultVibrator
    } else {
        @Suppress("DEPRECATION")
        getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator
    }

    val v = vibrator ?: return
    if (!v.hasVibrator()) return

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val amp = if (amplitude in 1..255 || amplitude == VibrationEffect.DEFAULT_AMPLITUDE)
            amplitude else VibrationEffect.DEFAULT_AMPLITUDE
        v.vibrate(VibrationEffect.createOneShot(durationMillis, amp))
    } else {
        @Suppress("DEPRECATION")
        v.vibrate(durationMillis)
    }
}
