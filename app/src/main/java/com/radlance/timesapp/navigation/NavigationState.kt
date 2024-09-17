package com.radlance.timesapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.serialization.Serializable

class NavigationState(
    val navHostController: NavHostController
) {
    fun <T: Any> navigateTo(route: @Serializable T) {
        navHostController.navigate(route) {
            launchSingleTop = true
            restoreState = true

            popUpTo(navHostController.graph.findStartDestination().id) {
                saveState = true
            }
        }
    }
}

@Composable
fun rememberNavigationState(
    navHostController: NavHostController = rememberNavController()
): NavigationState {
    return remember {
        NavigationState(navHostController)
    }
}