package com.radlance.timesapp.navigation

import androidx.annotation.Keep
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTimeFilled
import androidx.compose.material.icons.filled.Alarm
import androidx.compose.material.icons.filled.Bedtime
import androidx.compose.material.icons.filled.HourglassFull
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material.icons.outlined.AddAlarm
import androidx.compose.material.icons.outlined.Alarm
import androidx.compose.material.icons.outlined.Bedtime
import androidx.compose.material.icons.outlined.HourglassEmpty
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

@Serializable
@Keep
object Timer : CommonDestination {
    override val unselectedIcon: ImageVector = Icons.Outlined.HourglassEmpty
    override val selectedIcon: ImageVector = Icons.Filled.HourglassFull
}

@Serializable
@Keep
object Alarm : CommonDestination {
    override val unselectedIcon: ImageVector = Icons.Outlined.Bedtime
    override val selectedIcon: ImageVector = Icons.Filled.Bedtime
}