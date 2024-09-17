package com.radlance.timesapp

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.radlance.presentation.TimeViewModel
import com.radlance.timesapp.navigation.CommonNavGraph
import com.radlance.timesapp.navigation.NavigationState
import com.radlance.timesapp.navigation.Time
import com.radlance.timesapp.navigation.Timer
import com.radlance.timesapp.navigation.rememberNavigationState
import com.radlance.uikit.TimesAppTheme

@Composable
fun TimesApp(
    timeViewModel: TimeViewModel
) {
    val navigationState = rememberNavigationState()

    TimesAppTheme {
        Scaffold(
            bottomBar = { TimesBottomBar(navigationState) }
        ) { innerPadding ->
            CommonNavGraph(
                navController = navigationState.navHostController,
                timeViewModel = timeViewModel,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Composable
fun TimesBottomBar(
    navigationState: NavigationState
) {
    NavigationBar {
        val navBackStackEntry by navigationState.navHostController.currentBackStackEntryAsState()

        val items = listOf(Timer, Time)

        items.forEach { navigationItem ->
            val isSelected = navBackStackEntry?.destination?.hierarchy?.any {
                it.route == navigationItem::class.qualifiedName
            } ?: false

            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    if (!isSelected) {
                        navigationState.navigateTo(navigationItem)
                    }
                },
                icon = {
                    Icon(
                        imageVector = if (isSelected) {
                            navigationItem.selectedIcon
                        } else {
                            navigationItem.unselectedIcon
                        },
                        contentDescription = null
                    )
                }
            )
        }
    }
}