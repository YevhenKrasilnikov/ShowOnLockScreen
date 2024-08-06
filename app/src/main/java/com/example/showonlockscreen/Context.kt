package com.example.showonlockscreen

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.app.PendingIntent.getBroadcast
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.content.Intent.FLAG_RECEIVER_FOREGROUND
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.CATEGORY_CALL
import androidx.core.app.NotificationCompat.VISIBILITY_PUBLIC
import java.util.concurrent.TimeUnit

private const val WAKE_TIME = 20L
private const val SCHEDULE_TIME = 10L

fun Context.setState() {
    val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val timeInMillis = System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(WAKE_TIME)

    with(alarmManager) {
        setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            timeInMillis,
            getBroadcast(
                this@setState,
                0,
                Intent(this@setState, AlarmState::class.java),
                FLAG_IMMUTABLE
            )
        )
    }
    Toast.makeText(this, "Scheduled for ${WAKE_TIME + SCHEDULE_TIME} seconds", LENGTH_LONG).show()
}

fun Context.setService() {
    val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val timeInMillis = System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(WAKE_TIME)

    with(alarmManager) {
        setAlarmClock(
            AlarmManager.AlarmClockInfo(
                timeInMillis,
                PendingIntent.getActivity(
                    this@setService,
                    0,
                    Intent(this@setService, LockscreenActivity::class.java),
                    FLAG_IMMUTABLE or FLAG_UPDATE_CURRENT
                )
            ), PendingIntent.getService(
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
    val notification = NotificationCompat.Builder(this, "channelId")
        .setSmallIcon(android.R.drawable.arrow_up_float)
        .setContentTitle("title")
        .setContentText("description")
        .setPriority(NotificationCompat.PRIORITY_MAX)
        .setCategory(CATEGORY_CALL)
        .setVisibility(VISIBILITY_PUBLIC)
        .setShowWhen(false)
        .setFullScreenIntent(
            PendingIntent.getActivity(
                this,
                0,
                Intent(this, LockscreenActivity::class.java),
                FLAG_IMMUTABLE
            ), true
        )

    val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

    with(notificationManager) {
    notificationManager.buildChannel()
        notify(100, notification.build())
    }
//    service.startForeground(12, notification.build())
}

private fun NotificationManager.buildChannel() {
    val name = "example"
    val descriptionText = "demo"
    val importance = NotificationManager.IMPORTANCE_HIGH
    val channel = NotificationChannel("channelId", name, importance).apply {
        description = descriptionText
    }

    createNotificationChannel(channel)
}