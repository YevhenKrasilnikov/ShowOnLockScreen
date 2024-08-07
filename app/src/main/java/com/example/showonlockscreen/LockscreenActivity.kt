package com.example.showonlockscreen

import android.app.NotificationManager
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class LockscreenActivity : ComponentActivity() {

    private val connection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, service: IBinder) {

        }

        override fun onServiceDisconnected(name: ComponentName) {

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        window.addFlags(
            WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                    or WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                    or WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                    or WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
                    or WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON
        )
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Box(
                Modifier
                    .fillMaxSize()
                    .padding(32.dp), contentAlignment = Alignment.Center
            ) {
                Button(
                    modifier = Modifier
                        .fillMaxWidth(.6f)
                        .height(48.dp),
                    onClick = { onFinishClick() }) {
                    Text("finish")
                }
            }
        }
    }

    private fun onFinishClick() {
        release()
        val notificationManager =
            getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.cancelAll()
        finish()
    }

    override fun onResume() {
        super.onResume()
        bindService(Intent(this, AlarmService::class.java), connection, BIND_AUTO_CREATE)
    }

    override fun onPause() {
        super.onPause()
        unbindService(connection)
    }
}