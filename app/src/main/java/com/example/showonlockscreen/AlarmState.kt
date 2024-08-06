package com.example.showonlockscreen

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class AlarmState : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val result = goAsync()
        acquireWakeLock(context)
        CoroutineScope(
            SupervisorJob()
        ).launch() {
            handle(context)
            result.finish()
            release()
        }
    }

    private fun handle(context: Context) {
        context.setService()
    }
}