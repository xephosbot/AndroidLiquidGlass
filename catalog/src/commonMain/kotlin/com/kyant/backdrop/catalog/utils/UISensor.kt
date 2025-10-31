package com.kyant.backdrop.catalog.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset

@Composable
expect fun rememberUISensor(): UISensor

expect class UISensor {
    val gravityAngle: Float
    val gravity: Offset
}
