package com.example.showonlockscreen

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.CATEGORY_CALL
import androidx.core.app.NotificationCompat.VISIBILITY_PUBLIC

class Receiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val notification = NotificationCompat.Builder(context, "channelId")
            .setSmallIcon(android.R.drawable.arrow_up_float)
            .setContentTitle("title")
            .setContentText("description")
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setCategory(CATEGORY_CALL)
            .setVisibility(VISIBILITY_PUBLIC)
            .setShowWhen(false)
            .setFullScreenIntent(
                PendingIntent.getActivity(
                    context,
                    0,
                    Intent(context, LockscreenActivity::class.java),
                    FLAG_IMMUTABLE
                ), true
            )

        val notificationManager =
            context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        with(notificationManager) {
            buildChannel()
            notify(0, notification.build())
        }

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
}