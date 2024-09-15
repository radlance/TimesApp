package com.radlance.timesapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.radlance.presentation.TimeScreen
import com.radlance.presentation.TimeViewModel

@Composable
fun CommonNavGraph(
    navController: NavHostController,
    timeViewModel: TimeViewModel,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = CommonDestination.Time,
        modifier = modifier
    ) {
        composable<CommonDestination.Time> {
            TimeScreen(viewModel = timeViewModel)
        }

        composable<CommonDestination.Timer> {

        }
    }
}