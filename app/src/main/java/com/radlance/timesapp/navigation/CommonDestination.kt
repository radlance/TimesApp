package com.radlance.timesapp.navigation

import androidx.annotation.Keep
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTimeFilled
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material.icons.outlined.Timer
import androidx.compose.ui.graphics.vector.ImageVector
import kotlinx.serialization.Serializable

interface CommonDestination {
    val unselectedIcon: ImageVector
    val selectedIcon: ImageVector
}

@Serializable
@Keep
object Time : CommonDestination {
    override val unselectedIcon: ImageVector = Icons.Outlined.AccessTime
    override val selectedIcon: ImageVector = Icons.Filled.AccessTimeFilled
}

@Serializable
@Keep
object StopWatch : CommonDestination {
    override val unselectedIcon: ImageVector = Icons.Outlined.Timer
    override val selectedIcon: ImageVector = Icons.Filled.Timer
}