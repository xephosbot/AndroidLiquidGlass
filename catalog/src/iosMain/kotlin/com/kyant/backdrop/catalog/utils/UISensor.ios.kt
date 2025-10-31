package com.kyant.backdrop.catalog.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.useContents
import platform.CoreMotion.CMMotionManager
import platform.Foundation.NSTimer
import kotlin.math.PI
import kotlin.math.atan2
import kotlin.math.sqrt

@Composable
actual fun rememberUISensor(): UISensor {
    val uiSensor = remember { UISensor() }

    DisposableEffect(Unit) {
        uiSensor.start()
        onDispose { uiSensor.stop() }
    }

    return uiSensor
}

@OptIn(ExperimentalForeignApi::class)
actual class UISensor {
    private val motionManager = CMMotionManager()
    private var timer: NSTimer? = null

    private var _gravityAngle by mutableFloatStateOf(45f)
    private var _gravity by mutableStateOf(Offset.Zero)

    actual val gravityAngle: Float get() = _gravityAngle
    actual val gravity: Offset get() = _gravity

    fun start() {
        if (!motionManager.isAccelerometerAvailable()) return

        motionManager.startAccelerometerUpdates()

        timer = NSTimer.scheduledTimerWithTimeInterval(0.05, true) {
            motionManager.accelerometerData?.acceleration?.useContents {
                val x = this.x.toFloat()
                val y = this.y.toFloat()
                val norm = sqrt(x * x + y * y + 9.81f * 9.81f)

                val alpha = 0.5f
                val angle = atan2(y, x) * (180f / PI.toFloat())

                _gravityAngle = _gravityAngle * (1f - alpha) + angle * alpha
                _gravity = _gravity * (1f - alpha) + Offset(x / norm, y / norm) * alpha
            }
        }
    }

    fun stop() {
        motionManager.stopAccelerometerUpdates()
        timer?.invalidate()
        timer = null
    }
}