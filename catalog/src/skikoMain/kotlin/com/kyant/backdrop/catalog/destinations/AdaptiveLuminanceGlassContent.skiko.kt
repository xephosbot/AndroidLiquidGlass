package com.kyant.backdrop.catalog.destinations

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asSkiaBitmap

internal actual fun calculateImageLuminance(
    imageBitmap: ImageBitmap,
    width: Int,
    height: Int,
): Double {
    val srcBitmap = imageBitmap.asSkiaBitmap()
    val srcWidth = srcBitmap.width
    val srcHeight = srcBitmap.height

    val scaleX = srcWidth.toFloat() / width
    val scaleY = srcHeight.toFloat() / height

    var totalLuminance = 0.0

    for (y in 0 until height) {
        val srcY = (y * scaleY).toInt().coerceIn(0, srcHeight - 1)
        for (x in 0 until width) {
            val srcX = (x * scaleX).toInt().coerceIn(0, srcWidth - 1)
            val color = srcBitmap.getColor(srcX, srcY)

            val r = ((color shr 16) and 0xFF) / 255.0
            val g = ((color shr 8) and 0xFF) / 255.0
            val b = (color and 0xFF) / 255.0

            val luminance = 0.2126 * r + 0.7152 * g + 0.0722 * b
            totalLuminance += luminance
        }
    }

    return totalLuminance / (width * height)
}
