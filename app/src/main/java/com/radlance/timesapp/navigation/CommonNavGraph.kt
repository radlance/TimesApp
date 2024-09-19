package com.radlance.timesapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.radlance.presentation.Stopwatch
import com.radlance.presentation.TimeScreen

@Composable
fun CommonNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Time,
        modifier = modifier
    ) {
        composable<Time> {
            TimeScreen()
        }

        composable<StopWatch> {
            Stopwatch()
        }
    }
}