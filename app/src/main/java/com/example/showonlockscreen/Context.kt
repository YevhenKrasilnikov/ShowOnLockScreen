package com.example.showonlockscreen

import android.app.AlarmManager
import android.app.AlarmManager.RTC_WAKEUP
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.app.PendingIntent.FLAG_NO_CREATE
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_RECEIVER_FOREGROUND
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.CATEGORY_ALARM
import androidx.core.app.NotificationCompat.DEFAULT_LIGHTS
import androidx.core.app.NotificationCompat.PRIORITY_HIGH
import androidx.core.app.NotificationCompat.VISIBILITY_PUBLIC
import androidx.core.app.NotificationManagerCompat
import java.util.concurrent.TimeUnit

private const val WAKE_TIME = 200L
private const val SCHEDULE_TIME = 10L

fun Context.setState() {
    val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val timeInMillis = System.currentTimeMillis() + WAKE_TIME
    with(alarmManager) {
        setExactAndAllowWhileIdle(
            RTC_WAKEUP, timeInMillis, PendingIntent.getService(
                this@setState,
                20,
                Intent(this@setState, AlarmService::class.java).addFlags(
                    FLAG_RECEIVER_FOREGROUND
                ),
                FLAG_IMMUTABLE or FLAG_UPDATE_CURRENT
            )
        )
        setAlarmClock(
            AlarmManager.AlarmClockInfo(
                timeInMillis,
                PendingIntent.getActivity(
                    this@setState,
                    20,
                    Intent(this@setState, LockscreenActivity::class.java),
                    FLAG_IMMUTABLE or FLAG_UPDATE_CURRENT
                )
            ), PendingIntent.getBroadcast(
                this@setState,
                0,
                Intent(this@setState, AlarmState::class.java).setAction("ind"),
                FLAG_IMMUTABLE or FLAG_NO_CREATE
            )
        )
    }
}

fun Context.setService() {
    val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val timeInMillis = System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(WAKE_TIME)

    with(alarmManager) {
        setExact(
            RTC_WAKEUP, timeInMillis, PendingIntent.getService(
                this@setService,
                0,
                Intent(this@setService, AlarmService::class.java).addFlags(
                    FLAG_RECEIVER_FOREGROUND
                ),
                FLAG_IMMUTABLE or FLAG_UPDATE_CURRENT
            )
        )
    }
}

fun Context.scheduleWakeUp() {
    val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val timeInMillis = System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(SCHEDULE_TIME)

    with(alarmManager) {
        setAlarmClock(
            AlarmManager.AlarmClockInfo(
                timeInMillis,
                PendingIntent.getActivity(
                    this@scheduleWakeUp,
                    0,
                    Intent(this@scheduleWakeUp, LockscreenActivity::class.java),
                    FLAG_IMMUTABLE or FLAG_UPDATE_CURRENT
                )
            ), PendingIntent.getService(
                this@scheduleWakeUp,
                0,
                Intent(this@scheduleWakeUp, AlarmService::class.java).addFlags(
                    FLAG_RECEIVER_FOREGROUND
                ),
                FLAG_IMMUTABLE or FLAG_UPDATE_CURRENT
            )
        )
    }
}

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
                    FLAG_IMMUTABLE or FLAG_UPDATE_CURRENT
                )
            ), getReceiver()
        )
    }

}

fun Context.getReceiver(): PendingIntent {
    return PendingIntent.getBroadcast(this, 20, Intent(this, Receiver::class.java), FLAG_IMMUTABLE)
}

fun Context.showNotification(service: AlarmService) {
    val notification = NotificationCompat.Builder(service, "channelId")
        .setSmallIcon(android.R.drawable.arrow_up_float)
        .setContentTitle("title")
        .setContentText("description")
        .setPriority(PRIORITY_HIGH)
        .setCategory(CATEGORY_ALARM)
        .setVisibility(VISIBILITY_PUBLIC)
        .setShowWhen(false)
        .setOngoing(true)
        .setAutoCancel(false)
        .setDefaults(DEFAULT_LIGHTS)
        .setContentIntent(
            PendingIntent.getActivity(
                this,
                10,
                Intent(
                    this,
                    LockscreenActivity::class.java
                ).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_NO_USER_ACTION),
                FLAG_IMMUTABLE
            )
        )
        .setFullScreenIntent(
            PendingIntent.getActivity(
                this,
                10,
                Intent(
                    this,
                    LockscreenActivity::class.java
                ).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_NO_USER_ACTION),
                FLAG_IMMUTABLE
            ), true
        )

    val channel =
        NotificationChannel("channelId", "name", NotificationManager.IMPORTANCE_HIGH).apply {
            description = "descriptionText"
            lockscreenVisibility = VISIBILITY_PUBLIC
        }
    val notificationManager = NotificationManagerCompat.from(this)
    notificationManager.createNotificationChannel(channel)

    notificationManager.cancelAll()

    service.startForeground(12, notification.build())
}
