package com.radlance.timesapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.radlance.presentation.AlarmScreen
import com.radlance.presentation.CountdownTimer
import com.radlance.presentation.Stopwatch
import com.radlance.presentation.TimeScreen
import com.radlance.uikit.ContentType


@Composable
fun CommonNavGraph(
    navController: NavHostController,
    navigateTo: String?,
    contentType: ContentType,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = when (navigateTo) {
            null -> { Time }

            "STOPWATCH" -> { StopWatch }

            "ALARM" -> { Alarm }

            else -> { Timer }
        },
        modifier = modifier
    ) {
        composable<Time> { TimeScreen(contentType = contentType) }

        composable<StopWatch> { Stopwatch() }

        composable<Timer> { CountdownTimer() }

        composable<Alarm> { AlarmScreen() }
    }
}