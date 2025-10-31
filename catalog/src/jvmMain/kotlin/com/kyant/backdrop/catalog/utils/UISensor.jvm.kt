package com.kyant.backdrop.catalog.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset

@Composable
actual fun rememberUISensor(): UISensor {
    return remember { UISensor() }
}

actual class UISensor {
    private var _gravityAngle: Float by mutableFloatStateOf(45f)
    private var _gravity: Offset by mutableStateOf(Offset.Zero)

    actual val gravityAngle: Float get() = _gravityAngle
    actual val gravity: Offset get() = _gravity
}
