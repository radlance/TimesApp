package com.radlance.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
internal fun CustomButton(
    icon: ImageVector,
    onclick: () -> Unit,
    paddingValues: PaddingValues = PaddingValues(bottom = 30.dp)
) {
    Box(
        modifier = Modifier
            .padding(paddingValues)
            .clip(CircleShape)
            .size(75.dp)
            .background(MaterialTheme.colorScheme.primary)
            .clickable { onclick() }

    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier
                .size(45.dp)
                .align(Alignment.Center)
        )
    }
}