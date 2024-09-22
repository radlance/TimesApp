package com.radlance.timesapp

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        requestPermissions(arrayOf(Manifest.permission.POST_NOTIFICATIONS), 1)

        setContent {
            val screenToOpen = intent.getStringExtra("EXTRA_SCREEN")
            Log.d("MainActivity", "screen to open: $screenToOpen")
            TimesApp(screenToOpen)
        }
    }
}