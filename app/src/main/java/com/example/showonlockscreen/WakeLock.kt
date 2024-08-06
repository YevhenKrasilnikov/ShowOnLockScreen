package com.example.showonlockscreen

import android.annotation.SuppressLint
import android.content.Context
import android.os.PowerManager

lateinit var wl: PowerManager.WakeLock

@SuppressLint("InvalidWakeLockTag")
fun acquireWakeLock(context: Context) {
    val pm = context.getSystemService(Context.POWER_SERVICE) as PowerManager
    wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "TAG")
    wl.acquire(30 * 1000L)
}

fun release() {
    wl.release()
}