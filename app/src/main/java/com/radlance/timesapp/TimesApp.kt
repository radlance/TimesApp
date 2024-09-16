package com.radlance.timesapp

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.radlance.presentation.TimeViewModel
import com.radlance.timesapp.navigation.CommonDestination
import com.radlance.timesapp.navigation.CommonNavGraph
import com.radlance.timesapp.navigation.NavigationState
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
    val items = listOf(CommonDestination.Timer, CommonDestination.Time)
    var selectedItemIndex by rememberSaveable { mutableIntStateOf(0) }

    BottomAppBar {
        items.forEachIndexed { index, navigationItem ->

            NavigationBarItem(
                selected = selectedItemIndex == index,
                onClick = {
                    selectedItemIndex = index
                    navigationState.navigateTo(navigationItem)
                },
                icon = {
                    Icon(
                        imageVector = if (index == selectedItemIndex) {
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