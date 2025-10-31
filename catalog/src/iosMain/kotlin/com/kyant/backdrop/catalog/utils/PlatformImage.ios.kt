package com.kyant.backdrop.catalog.utils

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.get
import kotlinx.cinterop.reinterpret
import org.jetbrains.skia.Image
import platform.CoreGraphics.CGImageGetHeight
import platform.CoreGraphics.CGImageGetWidth
import platform.UIKit.UIImage
import platform.UIKit.UIImageJPEGRepresentation

@OptIn(ExperimentalForeignApi::class)
actual class PlatformImage(
    private val image: UIImage
) {
    actual val data: ByteArray
        get() {
            val imageData = UIImageJPEGRepresentation(image, COMPRESSION_QUALITY)
                ?: throw IllegalArgumentException("image data is null")
            val bytes = imageData.bytes ?: throw IllegalArgumentException("image bytes is null")
            val length = imageData.length

            val data: CPointer<ByteVar> = bytes.reinterpret()
            return ByteArray(length.toInt()) { index -> data[index] }
        }

    actual val width: Int
        get() {
            val cgImage = image.CGImage ?: throw IllegalArgumentException("UIImage has no CGImage")
            return CGImageGetWidth(cgImage).toInt()
        }

    actual val height: Int
        get() {
            val cgImage = image.CGImage ?: throw IllegalArgumentException("UIImage has no CGImage")
            return CGImageGetHeight(cgImage).toInt()
        }

    actual fun toImageBitmap(): ImageBitmap {
        val byteArray = data
        return Image.makeFromEncoded(byteArray).toComposeImageBitmap()
    }

    private companion object {
        const val COMPRESSION_QUALITY = 0.99
    }
}
