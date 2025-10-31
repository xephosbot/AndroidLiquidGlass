package com.kyant.backdrop.catalog.utils

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import org.jetbrains.skia.Image

actual class PlatformImage(
    actual val data: ByteArray
) {
    private val skiaImage: Image by lazy {
        Image.makeFromEncoded(data)
    }

    actual val width: Int get() = skiaImage.width
    actual val height: Int get() = skiaImage.height

    actual fun toImageBitmap(): ImageBitmap {
        return skiaImage.toComposeImageBitmap()
    }
}
