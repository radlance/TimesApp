package com.radlance.timesapp

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.radlance.timesapp.navigation.Alarm
import com.radlance.timesapp.navigation.CommonNavGraph
import com.radlance.timesapp.navigation.NavigationState
import com.radlance.timesapp.navigation.StopWatch
import com.radlance.timesapp.navigation.Time
import com.radlance.timesapp.navigation.Timer
import com.radlance.timesapp.navigation.rememberNavigationState
import com.radlance.uikit.ContentType
import com.radlance.uikit.TimesAppTheme

@Composable
fun TimesApp(
    navigateTo: String?,
    windowSize: WindowWidthSizeClass
) {
    val navigationState = rememberNavigationState()
    val contentType = if(windowSize == WindowWidthSizeClass.Expanded) {
        ContentType.Expanded
    } else {
        ContentType.Default
    }

    TimesAppTheme {
        Scaffold(
            bottomBar = { TimesBottomBar(navigationState) }
        ) { innerPadding ->
            CommonNavGraph(
                navController = navigationState.navHostController,
                navigateTo = navigateTo,
                contentType = contentType,
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

        val items = listOf(Time, StopWatch, Timer, Alarm)

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