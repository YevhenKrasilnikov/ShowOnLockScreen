package com.example.showonlockscreen

import android.Manifest.permission.POST_NOTIFICATIONS
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.os.Bundle
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

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (checkSelfPermission(POST_NOTIFICATIONS) != PERMISSION_GRANTED) {
            requestPermissions(arrayOf(POST_NOTIFICATIONS), 0)
        }
        enableEdgeToEdge()
        setContent {
            Box(Modifier.fillMaxSize().padding(48.dp), contentAlignment = Alignment.Center) {
                Button(modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                    onClick = { scheduleNotification() }) {
                    Text("Show screen with delay")
                }
            }
        }
    }
}

