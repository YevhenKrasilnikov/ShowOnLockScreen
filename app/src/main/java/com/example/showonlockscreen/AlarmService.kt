package com.example.showonlockscreen

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log

class AlarmService : Service() {

    private val binder: IBinder = Binder()
    private var isBound = false

    override fun onBind(intent: Intent?): IBinder {
        isBound = true
        return binder
    }

    override fun onUnbind(intent: Intent?): Boolean {
        isBound = false
        return super.onUnbind(intent)
    }

    override fun onCreate() {
        super.onCreate()
        Log.d("LLL", "service onCreate")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("LLL", "service onStartCommand")
        acquireWakeLock(this)
        sendBroadcast(Intent("ALARM"))
//        this.scheduleNotification()
        this.showNotification(this)
        return START_NOT_STICKY
    }
}