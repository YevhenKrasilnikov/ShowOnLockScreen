package com.example.showonlockscreen

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.content.Context
import android.content.Intent
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import java.util.concurrent.TimeUnit

private const val SCHEDULE_TIME = 15L

fun Context.scheduleNotification() {
    val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val timeInMillis = System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(SCHEDULE_TIME)

    with(alarmManager) {
        setAlarmClock(
            AlarmManager.AlarmClockInfo(
                timeInMillis,
                PendingIntent.getActivity(
                    this@scheduleNotification,
                    0,
                    Intent(this@scheduleNotification, LockscreenActivity::class.java),
                    FLAG_IMMUTABLE
                )
            ), getReceiver()
        )
    }
    Toast.makeText(this, "Scheduled for $SCHEDULE_TIME seconds", LENGTH_LONG).show()
}

fun Context.getReceiver(): PendingIntent {
    return PendingIntent.getBroadcast(this, 0, Intent(this, Receiver::class.java), FLAG_IMMUTABLE)
}