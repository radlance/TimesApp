package com.radlance.timesapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.radlance.presentation.TimeScreen
import com.radlance.uikit.TimesAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TimesAppTheme {
                Scaffold { paddingValues ->
                    TimeScreen(modifier = Modifier.padding(paddingValues))
                }
            }
        }
    }
}