package com.example.showonlockscreen

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class AlarmState : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if ("ind" == intent.action) {
            return
        }
//        val result = goAsync()
//        acquireWakeLock(context)
//        CoroutineScope(
//            SupervisorJob()
//        ).launch() {
//            handle(context)
//            result.finish()
//            release()
//        }
    }

    private fun handle(context: Context) {
        context.setService()
    }
}