package com.radlance.timesapp

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.radlance.presentation.TimeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<TimeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val requestPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted ->
            if (isGranted) {
                viewModel.getLocation()
                viewModel.startUpdatingTime()
            }
        }

        requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)

        enableEdgeToEdge()
        setContent {
            TimesApp(timeViewModel = viewModel)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.stopUpdatingTime()
    }
}